package pl.kamrar.zarcie.example.complex.verticle.config;

import io.vertx.core.Future;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Router;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalRouter extends AbstractVerticle {

    private static Router router;

    @Override
    public void start(Future<Void> future) throws Exception {
        router = Router.router(vertx);
    }

    @Bean
    Router router() {
        return router;
    }
}
