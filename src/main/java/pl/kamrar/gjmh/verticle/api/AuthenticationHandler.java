package pl.kamrar.gjmh.verticle.api;

import io.vertx.core.json.Json;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kamrar.gjmh.model.persisted.entity.User;
import pl.kamrar.gjmh.model.persisted.repository.UserRepository;
import pl.kamrar.gjmh.model.transfered.GoogleUserDTO;
import pl.kamrar.gjmh.verticle.helper.DefaultVerticle;

@Component
public class AuthenticationHandler extends DefaultVerticle {

    @Autowired
    private Router router;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void start() throws Exception {
        router.route("/api/v1/authentication*").handler(BodyHandler.create());
        router.post("/api/v1/authentication").handler(this::authenticate);
    }

    private void authenticate(RoutingContext routingContext) {
        final GoogleUserDTO googleUserDTO = Json.decodeValue(routingContext.getBodyAsString(),
                GoogleUserDTO.class);
        userRepository.save(new User(googleUserDTO.getName(), googleUserDTO.getSurname(), googleUserDTO.getEmail()));
        routingContext.response().setStatusCode(200).end();
    }
}
