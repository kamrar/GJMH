package pl.kamrar.zarcie.example.complex.verticle;

import io.vertx.rxjava.ext.web.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kamrar.zarcie.example.complex.verticle.helper.DefaultVerticle;

/*
    Complex use of Vert.x server with multiple endpoints. Probably default our dev
 */
@Component
public class ComplexStaticServer extends DefaultVerticle {

    @Autowired
    private Router router;

    @Override
    public void start() throws Exception {
        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8080);
    }
}
