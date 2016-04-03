package pl.kamrar.gjmh.verticle.config;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.mongo.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kamrar.gjmh.verticle.helper.DefaultVerticle;

@Configuration
public class GlobalMongoClient extends DefaultVerticle{

    MongoClient mongoClient;

    @Override
    public void start() throws Exception {

        JsonObject config = new JsonObject()
                .put("db_name", "gjmh")
                .put("host", "localhost")
                .put("port", 27017);

        mongoClient = MongoClient.createShared(vertx, config);

    }

    @Bean
    MongoClient mongoClient(){ return mongoClient;}
}
