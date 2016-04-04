package pl.kamrar.gjmh.verticle.api;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kamrar.gjmh.model.repository.OrderRepository;
import pl.kamrar.gjmh.verticle.helper.DefaultVerticle;
import rx.Observable;

@Component
public class OrderHandler extends DefaultVerticle {

    private static final String API_V1_ORDER = "/api/v1/order";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Router router;

    @Override
    public void start() throws Exception {
        router.route("/api/v1/order*").handler(BodyHandler.create());
        router.get(API_V1_ORDER + "/:id").handler(this::find);
        router.get(API_V1_ORDER).handler(this::findAll);
        router.post(API_V1_ORDER).handler(this::insert);
        router.put(API_V1_ORDER + "/:id").handler(this::update);
    }

    public void find(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        orderRepository.findOne(id).subscribe(entries -> {
            if (entries.isEmpty()) {
                routingContext.response()
                        .setStatusCode(404).end();
            } else {
                routingContext.response()
                        .setStatusCode(200)
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(entries));
            }
        });
    }

    public void findAll(RoutingContext routingContext) {
        orderRepository.find().subscribe(entries -> {
            if (entries.isEmpty()) {
                routingContext.response()
                        .setStatusCode(404).end();
            } else {
                routingContext.response()
                        .setStatusCode(200)
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(entries));
            }
        });
    }

    public void insert(RoutingContext routingContext) {
        JsonObject order = routingContext.getBodyAsJson();
        //TODO json validation
        orderRepository.insert(order).subscribe(s -> {
            if (s.isEmpty()) {
                routingContext.response().setStatusCode(409).end();
            } else {
                routingContext.response().setStatusCode(200).end();
            }
        });
    }

    public void update(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        JsonObject order = routingContext.getBodyAsJson();
        //TODO json validation
        orderRepository.update(id, order).subscribe();
        routingContext.response().end();
    }
}
