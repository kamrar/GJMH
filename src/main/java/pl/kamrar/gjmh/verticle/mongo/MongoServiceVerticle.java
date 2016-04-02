package pl.kamrar.gjmh.verticle.mongo;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.mongo.MongoClient;
import org.springframework.stereotype.Component;
import pl.kamrar.gjmh.verticle.helper.DefaultVerticle;

@Component
public class MongoServiceVerticle extends DefaultVerticle{

    MongoClient mongoClient;

    @Override
    public void start() throws Exception {

        JsonObject config = new JsonObject()
                .put("host", "localhost")
                .put("port", 27017);

        mongoClient = MongoClient.createShared(vertx, config);

    }
}
