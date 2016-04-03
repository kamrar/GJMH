package pl.kamrar.gjmh.model.repository;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.mongo.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.kamrar.gjmh.verticle.helper.DefaultVerticle;

@Repository
public class OrderRepository extends DefaultVerticle {

    @Autowired
    MongoClient mongoClient;

    @Override
    public void start() throws Exception {


        JsonObject document = new JsonObject().put("name", "Pizza");

        mongoClient.save("order", document, res -> {

            if (res.succeeded()) {
                String id = res.result();
                System.out.println("Saved order with id " + id);
            } else {
                res.cause().printStackTrace();
            }
        });
    }



}
