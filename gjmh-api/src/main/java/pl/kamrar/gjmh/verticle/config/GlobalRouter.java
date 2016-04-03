package pl.kamrar.gjmh.verticle.config;

import io.vertx.core.Future;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Router;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletableFuture;

@Configuration
public class GlobalRouter extends AbstractVerticle {

    private static CompletableFuture<Router> router;

    @Override
    public void start(Future<Void> future) throws Exception {
        router = CompletableFuture.supplyAsync(() -> Router.router(vertx));
    }

    @Bean
    Router router() throws Exception {
        return router.get();
    }
}
