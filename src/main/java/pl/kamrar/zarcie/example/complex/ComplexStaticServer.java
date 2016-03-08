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
    private Router globalRouter;

    @Override
    public void start() throws Exception {
        vertx.createHttpServer()
                .requestHandler(globalRouter::accept)
                .listen(8080);
    }
}
