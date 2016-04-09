package pl.kamrar.gjmh.verticle.api.auth;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kamrar.gjmh.model.entity.UserProfile;
import pl.kamrar.gjmh.model.repository.UserRepository;
import pl.kamrar.gjmh.verticle.helper.DefaultVerticle;

import static pl.kamrar.gjmh.verticle.api.auth.OAuth2ProviderType.provider;

@Component
public class OAuth2Provider extends DefaultVerticle implements AuthProvider {

    private static final String TOKEN = "token";
    private static final String PROVIDER = "provider";

    @Autowired
    private UserRepository userRepository;

    @Override
    public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {
        vertx.executeBlocking(userFuture -> {
            final String token = (String) authInfo.remove(TOKEN);
            final String provider = (String) authInfo.remove(PROVIDER);
            OAuth2ProviderType oAuth2ProviderType = provider(provider);

            vertx.createHttpClient(new HttpClientOptions().setSsl(true)).getNow(443, oAuth2ProviderType.host(), oAuth2ProviderType.uri() + token, response ->
                    response.bodyHandler(body -> {
                        if (oAuth2ProviderType.verify(body)) {
                            userFuture.complete(auth(authInfo.encode()));
                        }
                    }));
        }, resultHandler);
    }

    private UserProfile auth(String bodyAsString) {
        final UserProfile userProfile = Json.decodeValue(bodyAsString,
                UserProfile.class);
        UserProfile authUserProfile = userRepository.findByEmail(userProfile.getEmail());
        if (authUserProfile == null) {
            authUserProfile = userRepository.save(userProfile);
        }
        return authUserProfile;
    }

    @Override
    public void start() throws Exception {

    }
}
