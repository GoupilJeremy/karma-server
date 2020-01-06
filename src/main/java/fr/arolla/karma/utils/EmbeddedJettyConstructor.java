package fr.arolla.karma.utils;

import org.eclipse.jetty.server.AbstractNCSARequestLog;
import spark.embeddedserver.jetty.EmbeddedJettyFactory;

public class EmbeddedJettyConstructor {
    AbstractNCSARequestLog requestLog;

    public EmbeddedJettyConstructor(AbstractNCSARequestLog requestLog) {
        this.requestLog = requestLog;
    }

    EmbeddedJettyFactory create() {
        EmbeddedJettyServerFactory embeddedJettyServerFactory = new EmbeddedJettyServerFactory(this);

        return new EmbeddedJettyFactory(embeddedJettyServerFactory);
    }
}
