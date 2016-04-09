package pl.kamrar.gjmh.verticle;

import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kamrar.gjmh.verticle.helper.DefaultVerticle;

import javax.annotation.PreDestroy;

/*
    Complex use of Vert.x server with multiple endpoints. Probably default our dev
 */
@Component
public class ComplexStaticServer extends DefaultVerticle {

    @Autowired
    private Router router;
    private HttpServer httpServer;

    @Override
    public void start() throws Exception {
        httpServer = vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8080);
    }

    @PreDestroy
    public void onClose() {
        httpServer.close();
    }
}
