package fr.arolla.karma;

import com.beust.jcommander.Parameter;

public class CommandLineOptions {
    @Parameter(names = "--debug")
    boolean debug = false;

    @Parameter(names = {"--service-port"})
    Integer servicePort = 4567;

    @Parameter(names = {"--database"})
    String database = "karma";

    @Parameter(names = {"--db-host"})
    String dbHost = "localhost";

    @Parameter(names = {"--db-username"})
    String dbUsername = "karma";

    @Parameter(names = {"--db-password"})
    String dbPassword = "arolla";

    @Parameter(names = {"--db-port"})
    Integer dbPort = 5432;
}
