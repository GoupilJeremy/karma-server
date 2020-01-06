package fr.arolla.karma;

import com.beust.jcommander.JCommander;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.arolla.karma.utils.SparkUtils;
import org.apache.log4j.Logger;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;


import java.io.IOException;
import java.util.UUID;


import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.get;


public class App 
{
    private static final Logger logger = Logger.getLogger(App.class.getCanonicalName());
    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(data);
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }
    public static void main( String[] args )
    {

        SparkUtils.createServerWithRequestLog(logger);

        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, args);


        port(options.servicePort);

        Sql2o sql2o = new Sql2o("jdbc:postgresql://" + options.dbHost + ":" + options.dbPort + "/" + options.database,
                options.dbUsername, options.dbPassword, new PostgresQuirks() {
            {
                // make sure we use default UUID converter.
                converters.put(UUID.class, new UUIDConverter());
            }
        });

        Model model = new Sql2oModel(sql2o);

        get("/hello", (request, response) -> {
            response.status(200);
            return "world";
        });

        // insert a post (using HTTP post method)
        post("/posts", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            NewPostPayload creation = mapper.readValue(request.body(), NewPostPayload.class);
            if (!creation.isValid()) {
                response.status(HTTP_BAD_REQUEST);
                return "";
            }
            UUID id = model.createPost(creation.getTitle(), creation.getContent(), creation.getCategories());
            response.status(200);
            response.type("application/json");
            return id;
        });

        // get all post (using HTTP get method)
        get("/posts", (request, response) -> {
            response.status(200);
            response.type("application/json");
            return dataToJson(model.getAllPosts());
        });

        // get all event (using HTTP get method)
        get("/events", (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Credentials", "true");
            response.header("Access-Control-Allow-Methods", "POST, GET");
            response.header("Access-Control-Allow-Headers", "Content-Type");
            response.header("Access-Control-Expose-Headers", "X-Total-Count");
            response.header("X-Total-Count", model.getAllEvents().size() + "");
            response.status(200);
            response.type("application/json");
            return dataToJson(model.getAllEvents());
        });

        post("/posts/:uuid/comments", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            NewCommentPayload creation = mapper.readValue(request.body(), NewCommentPayload.class);
            if (!creation.isValid()) {
                response.status(HTTP_BAD_REQUEST);
                return "";
            }
            UUID post = UUID.fromString(request.params(":uuid"));
            if (!model.existPost(post)){
                response.status(400);
                return "";
            }
            UUID id = model.createComment(post, creation.getAuthor(), creation.getContent());
            response.status(200);
            response.type("application/json");
            return id;
        });

        get("/posts/:uuid/comments", (request, response) -> {
            UUID post = UUID.fromString(request.params(":uuid"));
            if (!model.existPost(post)) {
                response.status(400);
                return "";
            }
            response.status(200);
            response.type("application/json");
            return dataToJson(model.getAllCommentsOn(post));
        });
    }
}
