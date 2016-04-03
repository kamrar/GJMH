package pl.kamrar.gjmh.verticle.api;

import io.vertx.core.json.Json;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kamrar.gjmh.model.Product;
import pl.kamrar.gjmh.verticle.helper.DefaultVerticle;

@Component
public class ProductHandler extends DefaultVerticle {

    private static final String API_V1_PRODUCT = "/api/v1/product";

    @Autowired
    private Router router;

    private Product product = new Product(0, "test");

    @Override
    public void start() throws Exception {
        router.get(API_V1_PRODUCT).handler(this::getAll);
        router.get(API_V1_PRODUCT + "/:id").handler(this::getOne);
        router.post(API_V1_PRODUCT).handler(this::addOne);
        router.route("/*").handler(StaticHandler.create("web"));
    }

    private void getAll(RoutingContext routingContext) {
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(product));
    }

    private void getOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        if (Integer.valueOf(id) != 0) {
            routingContext.response().setStatusCode(404).end();
        } else {
            routingContext.response()
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(product));
        }
    }

    private void addOne(RoutingContext routingContext) {
        routingContext.response()
                .putHeader("content-type", "text/html")
                .end("<h1>Chuja, nie dodam</h1>");
    }
}
