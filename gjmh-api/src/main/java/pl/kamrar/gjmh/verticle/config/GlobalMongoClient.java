package pl.kamrar.gjmh.verticle.config;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.mongo.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletableFuture;

@Configuration
public class GlobalMongoClient extends AbstractVerticle {

    private static CompletableFuture<MongoClient> mongoClient;

    @Override
    public void start(Future<Void> future) throws Exception {
        JsonObject config = new JsonObject()
                .put("db_name", "gjmh")
                .put("host", "localhost")
                .put("port", 27017);
        mongoClient = CompletableFuture.supplyAsync(() -> MongoClient.createShared(vertx, config));

    }

    @Bean
    MongoClient mongoClient() throws Exception {
        return mongoClient.get();
    }
}
