package pl.kamrar.zarcie.example.complex;

import io.vertx.rxjava.core.Future;
import io.vertx.rxjava.ext.web.Router;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class GlobalRouter extends DefaultVerticle {

    private Router router;

    @Override
    public void start() throws Exception {
        router = Router.router(vertx);
    }

    public Router router() {
        Future.future().complete(router);
        return router;
    }
}
