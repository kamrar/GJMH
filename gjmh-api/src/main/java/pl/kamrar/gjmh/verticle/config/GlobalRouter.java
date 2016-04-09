package pl.kamrar.gjmh.verticle.config;

import io.vertx.core.Future;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.CookieHandler;
import io.vertx.rxjava.ext.web.handler.SessionHandler;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import io.vertx.rxjava.ext.web.sstore.LocalSessionStore;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletableFuture;

@Configuration
public class GlobalRouter extends AbstractVerticle {

    private static CompletableFuture<Router> router;

    @Override
    public void start(Future<Void> future) {
        router = CompletableFuture.supplyAsync(() -> {
            Router localRouter = Router.router(vertx);
            localRouter.route().handler(CookieHandler.create());
            localRouter.route().handler(BodyHandler.create());
            localRouter.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
            localRouter.route("/app/*").handler(StaticHandler.create("web"));
            return localRouter;
        });
    }

    @Bean
    @SneakyThrows
    Router router() {
        return router.get();
    }
}
