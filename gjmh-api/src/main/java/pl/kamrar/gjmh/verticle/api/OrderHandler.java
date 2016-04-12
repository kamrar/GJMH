package pl.kamrar.gjmh.verticle.api;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kamrar.gjmh.model.Model;
import pl.kamrar.gjmh.model.Order;
import pl.kamrar.gjmh.model.Product;
import pl.kamrar.gjmh.model.repository.OrderRepository;
import pl.kamrar.gjmh.model.repository.ProductRepository;
import pl.kamrar.gjmh.verticle.helper.DefaultVerticle;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Component
public class OrderHandler extends DefaultVerticle {

    private static final String API_V1_ORDER = "/api/v1/order";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Router router;

    @Override
    public void start() throws Exception {
        router.route(API_V1_ORDER + "*").handler(BodyHandler.create());
        router.get(API_V1_ORDER + "/:id").handler(this::find);
        router.get(API_V1_ORDER).handler(this::findAll);
        router.post(API_V1_ORDER).handler(this::insert);
        router.put(API_V1_ORDER + "/:id").handler(this::update);
        router.delete(API_V1_ORDER + "/:id").handler(this::remove);
    }

    public void find(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        orderRepository.find(id).subscribe(entries -> {
            if (entries == null) {
                routingContext.response()
                        .setStatusCode(404).end();
            } else {
                productRepository.findAll(new FindOptions(new JsonObject().put("order_id", id))).subscribe(jsonObjects -> {
                    entries.put("product_list", jsonObjects);
                    routingContext.response()
                            .setStatusCode(200)
                            .putHeader("content-type", "application/json; charset=utf-8")
                            .end(Json.encodePrettily(entries));
                });
            }
        });
    }

    public void findAll(RoutingContext routingContext) {
        JsonObject findOptions = new JsonObject();

        String limit = routingContext.request().getParam("limit");
        if (StringUtils.isNumeric(limit)) {
            findOptions.put("limit", Integer.parseInt(limit));
        }

        String offset = routingContext.request().getParam("offset");
        if (StringUtils.isNumeric(offset)) {
            findOptions.put("skip", Integer.parseInt(offset));
        }

        orderRepository.findAll(new FindOptions(findOptions)).subscribe(orderJsons -> {
            if (orderJsons.isEmpty()) {
                routingContext.response()
                        .setStatusCode(404).end();
            } else {
                orderJsons.forEach(orderJson -> {
                    String orderId = orderJson.getString("_id");
                    productRepository.findAll(new FindOptions(new JsonObject().put("order_id", orderId))).subscribe(productJsons -> {
                        orderJson.put("product_list", productJsons);
                        routingContext.response()
                                .setStatusCode(200)
                                .putHeader("content-type", "application/json; charset=utf-8")
                                .end(Json.encodePrettily(orderJsons));
                    });
                });
            }
        });
    }

    public void insert(RoutingContext routingContext) {
        final Order order = Order.order(routingContext.getBodyAsString());
        Set<ConstraintViolation<Model>> constraintViolations = order.valid();
        HttpServerResponse response = routingContext.response();

        if (constraintViolations.isEmpty()) {
            orderRepository.insert(routingContext.getBodyAsJson()).subscribe(s -> {
                if (s.isEmpty()) {
                    response.setStatusCode(409).end();
                } else {
                    response.setStatusCode(200).end();
                }
            });
        } else {
            JsonObject bodyJson = new JsonObject();
            constraintViolations.stream().forEach(constraintViolation ->
                    bodyJson.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()));
            response
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .setStatusCode(400)
                    .end(bodyJson.encodePrettily());
        }
    }

    public void update(RoutingContext routingContext) {
        final Order order = Order.order(routingContext.getBodyAsJson().encodePrettily());
        Set<ConstraintViolation<Model>> constraintViolations = order.valid();
        HttpServerResponse response = routingContext.response();

        if (constraintViolations.isEmpty()) {
            String orderId = routingContext.request().getParam("id");
            orderRepository.find(orderId).subscribe(entries -> {
                if (entries == null) {
                    response.setStatusCode(204).end();
                } else {
                    JsonObject orderJson = routingContext.getBodyAsJson();
                    orderRepository.update(orderId, orderJson);
                    response.setStatusCode(200).end();
                }
            });
        } else {
            JsonObject bodyJson = new JsonObject();
            constraintViolations.stream().forEach(constraintViolation ->
                    bodyJson.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()));
            response
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .setStatusCode(400)
                    .end(bodyJson.encodePrettily());
        }
    }

    public void remove(RoutingContext routingContext) {
        String orderId = routingContext.request().getParam("id");
        orderRepository.find(orderId).subscribe(entries -> {
            if (entries == null) {
                routingContext.response().setStatusCode(204).end();
            } else {
                orderRepository.remove(orderId);
                productRepository.findAll(new FindOptions(new JsonObject().put("order_id", orderId))).subscribe(productJsons -> {
                    productJsons.forEach(productJson -> {
                        String productId = Product.product(productJson.encodePrettily()).get_id();
                        productRepository.remove(productId);
                        routingContext.response().setStatusCode(200).end();
                    });
                });
            }
        });
    }
}
