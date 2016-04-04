package pl.kamrar.gjmh.model.repository;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.mongo.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.kamrar.gjmh.verticle.helper.DefaultVerticle;
import rx.Observable;
import java.util.List;

@Repository
public class OrderRepository extends DefaultVerticle {

    @Autowired
    private MongoClient mongoClient;

    @Override
    public void start() throws Exception {}

    public Observable<String> insert(JsonObject order) {
        return mongoClient.insertObservable("order", order);
    }

    public Observable<Void> update(String id, JsonObject order) {
        order.put("_id", id);
        return mongoClient.updateObservable("order", new JsonObject().put("_id", id), new JsonObject().put("$set", order));
    }

    public Observable<JsonObject> findOne(String id) {
        return mongoClient.findOneObservable("order", new JsonObject().put("_id", id), null);
    }

    public Observable<List<JsonObject>> find() {
        return mongoClient.findObservable("order", new JsonObject());
    }
}
