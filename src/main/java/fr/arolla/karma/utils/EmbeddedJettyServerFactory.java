package fr.arolla.karma.utils;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import spark.embeddedserver.jetty.JettyServerFactory;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class EmbeddedJettyServerFactory implements JettyServerFactory {
    private EmbeddedJettyConstructor embeddedJettyFactoryConstructor;

    EmbeddedJettyServerFactory(EmbeddedJettyConstructor embeddedJettyFactoryConstructor) {
        this.embeddedJettyFactoryConstructor = embeddedJettyFactoryConstructor;
    }

    @Override
    public Server create(int maxThreads, int minThreads, int threadTimeoutMillis) {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        Server server;
        if (maxThreads > 0) {
            int max = maxThreads > 0 ? maxThreads : 200;
            int min = minThreads > 0 ? minThreads : 8;
            int idleTimeout = threadTimeoutMillis > 0 ? threadTimeoutMillis : '\uea60';
            server = new Server(new QueuedThreadPool(max, min, idleTimeout));
        } else {
            server = new Server();
        }
        server.setHandler(context);

        // removed CORS problems
        FilterHolder filterHolder = context.addFilter(org.eclipse.jetty.servlets.CrossOriginFilter
                .class, "/*", EnumSet.of(DispatcherType.REQUEST));
        filterHolder.setInitParameter("allowedOrigins", "*");

        server.setRequestLog(embeddedJettyFactoryConstructor.requestLog);
        return server;
    }

    @Override
    public Server create(ThreadPool threadPool) {
        return new Server(threadPool);
    }
}
