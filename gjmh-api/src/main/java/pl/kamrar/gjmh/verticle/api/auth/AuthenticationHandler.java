package pl.kamrar.gjmh.verticle.api.auth;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.auth.AuthProvider;
import io.vertx.rxjava.ext.auth.User;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.Session;
import io.vertx.rxjava.ext.web.handler.RedirectAuthHandler;
import io.vertx.rxjava.ext.web.handler.UserSessionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kamrar.gjmh.verticle.helper.DefaultVerticle;

@Component
public class AuthenticationHandler extends DefaultVerticle {

    @Autowired
    private Router router;

    @Autowired
    private OAuth2Provider oAuth2Provider;

    @Override
    public void start() throws Exception {
        final AuthProvider authProvider = AuthProvider.newInstance(oAuth2Provider);
        router.route().handler(UserSessionHandler.create(authProvider));
        router.route("/api/v1/private/*").handler(RedirectAuthHandler.create(authProvider, "/app/index.html"));
        router.route("/api/v1/login").handler(context -> authProvider.authenticate(context.getBodyAsJson(), resultHandler(context)));
        router.route("/api/v1/logout").handler(context -> {
            context.clearUser();
            context.response().putHeader("location", "/").setStatusCode(302).end();
        });
    }

    private Handler<AsyncResult<User>> resultHandler(RoutingContext context) {
        return res -> {
            if (res.succeeded()) {
                User user = res.result();
                context.setUser(user);

                final Session session = context.session();
                if (session != null) {
//                    String returnURL = session.remove("returnURL");
//                    context.response().putHeader("location", returnURL).setStatusCode(302).end();
//                    return;
                }
                context.response().putHeader("location", "/api/v1/private/product").setStatusCode(302).end();
            } else {
                context.fail(403);
            }
        };
    }
}
