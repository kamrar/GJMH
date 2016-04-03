package pl.kamrar.gjmh.verticle.api.auth;

import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.Json;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kamrar.gjmh.model.entity.User;
import pl.kamrar.gjmh.model.repository.UserRepository;
import pl.kamrar.gjmh.verticle.helper.DefaultVerticle;

@Component
public class AuthenticationHandler extends DefaultVerticle {

    private static final String TOKEN = "token";
    private static final String PROVIDER = "provider";

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
        final String token = routingContext.request().getHeader(TOKEN);
        final String provider = routingContext.request().getHeader(PROVIDER);

        AuthProvider authProvider = AuthProvider.provider(provider);

        vertx.createHttpClient(new HttpClientOptions().setSsl(true)).getNow(443, authProvider.host(), authProvider.uri() + token, response ->
                response.bodyHandler(body -> {
                    if (authProvider.verify(body)) {
                        auth(routingContext.getBodyAsString());
                        routingContext.response().setStatusCode(200).end();
                    } else {
                        routingContext.response().setStatusCode(401).end();
                    }
                }));
    }

    private void auth(String bodyAsString) {
        final User user = Json.decodeValue(bodyAsString,
                User.class);
        User authUser = userRepository.findByEmail(user.getEmail());
        if (authUser == null) {
            authUser = userRepository.save(user);
        }
    }
}
