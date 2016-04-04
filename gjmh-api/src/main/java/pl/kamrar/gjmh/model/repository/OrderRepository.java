package pl.kamrar.gjmh.model.repository;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.mongo.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.kamrar.gjmh.model.Order;
import pl.kamrar.gjmh.model.enumerate.OrderStatus;
import pl.kamrar.gjmh.verticle.helper.DefaultVerticle;
import rx.Observable;
import rx.functions.Action1;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public class OrderRepository extends DefaultVerticle {

    String dupa;

    @Autowired
    private MongoClient mongoClient;

    @Override
    public void start() throws Exception {

        //Order order1 = new Order("moje zamowienie 1", "Plamira", "www.palmira.pl", LocalDateTime.now(), LocalDateTime.now(), OrderStatus.OPEN, null);

        //JsonObject orderJson1 = new JsonObject(Json.encode(order1));

    }

    public void insert(JsonObject order) {
        mongoClient.insertObservable("order", order);
    }

    public Observable<JsonObject> findOne(String id) {
        return mongoClient.findOneObservable("order", new JsonObject().put("_id", id), null);
    }

    public Observable<List<JsonObject>> find() {
        return mongoClient.findObservable("order", new JsonObject());
    }
}
