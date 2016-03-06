package pl.kamrar.zarcie.example.simple;

import io.vertx.core.Future;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.Router;
import org.springframework.stereotype.Component;

/*
    Simple use of Vert.x server with only one endpoint
 */
@Component
public class SimpleStaticServer extends AbstractVerticle {
    @Override
    public void start(Future<Void> future) throws Exception {
        Router router = Router.router(vertx);
        router.route("/api/v1").handler(event -> {
            HttpServerResponse response = event.response();
            response.putHeader("content-type", "text/html")
                    .end("<h1>Kutas wam w oko</h1>");
        });
        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8080);
    }
}
