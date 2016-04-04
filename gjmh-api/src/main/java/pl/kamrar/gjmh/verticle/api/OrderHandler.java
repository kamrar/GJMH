package pl.kamrar.gjmh.verticle.api;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kamrar.gjmh.model.repository.OrderRepository;
import pl.kamrar.gjmh.verticle.helper.DefaultVerticle;

@Component
public class OrderHandler extends DefaultVerticle {

    private static final String API_V1_ORDER = "/api/v1/order";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Router router;

    @Override
    public void start() throws Exception {
        router.get(API_V1_ORDER + "/:id").handler(this::getOne);
        router.get(API_V1_ORDER).handler(this::getAll);
    }

    public void getOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        orderRepository.findOne(id).subscribe(entries -> {
            routingContext.response()
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(entries));
        });
    }

    public void getAll(RoutingContext routingContext) {
        orderRepository.find().subscribe(entries -> {
            routingContext.response()
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(entries));
        });
    }
}
