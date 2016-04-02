package pl.kamrar.gjmh.verticle.mongo;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.mongo.MongoClient;
import pl.kamrar.gjmh.verticle.helper.DefaultVerticle;

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
