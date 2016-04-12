package pl.kamrar.gjmh.verticle.api;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kamrar.gjmh.model.Model;
import pl.kamrar.gjmh.model.Product;
import pl.kamrar.gjmh.model.repository.ProductRepository;
import pl.kamrar.gjmh.verticle.helper.DefaultVerticle;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Component
public class ProductHandler extends DefaultVerticle {

    private static final String API_V1 = "/api/v1/product";

    @Autowired
    private Router router;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void start() throws Exception {
        router.get(API_V1 + "/:id").handler(this::find);
        router.route(API_V1 + "/order/*").handler(BodyHandler.create());
        router.post(API_V1 + "/order/:orderId").handler(this::insert);
        router.route("/*").handler(StaticHandler.create("web"));
    }

    public void find(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        productRepository.find(id).subscribe(entries -> {
            if (entries == null) {
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
        String orderId = routingContext.request().getParam("orderId");
        JsonObject productJson = routingContext.getBodyAsJson().put("order_id", orderId);
        final Product product = Product.product(productJson.encodePrettily());

        Set<ConstraintViolation<Model>> constraintViolations = product.valid();
        HttpServerResponse response = routingContext.response();

        if (constraintViolations.isEmpty()) {
            productRepository.insert(productJson).subscribe(s -> {
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
}
