package pl.kamrar.zarcie.example.complex;

import io.vertx.rxjava.ext.web.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
    Complex use of Vert.x server with multiple endpoints. Probably default our dev
 */
@Component
public class ComplexStaticServer extends DefaultVerticle {

    @Autowired
    private GlobalRouter globalRouter;

    @Override
    public void start() throws Exception {
        Router router = globalRouter.router();
        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8080);
    }
}
