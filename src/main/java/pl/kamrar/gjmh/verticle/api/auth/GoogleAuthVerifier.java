package pl.kamrar.gjmh.verticle.api.auth;

import com.fasterxml.jackson.databind.JsonNode;
import io.vertx.core.json.Json;
import lombok.SneakyThrows;

import java.util.Objects;

public class GoogleAuthVerifier implements AuthVerifier {

    private static final String EMAIL_VERIFIED = "email_verified";
    private static final String AUD = "aud";
    public static final String AUTH_GOOGLE_AUD_VALUE = "94625739913-sv6j34ghs9lihlam9s89nue303todlq1.apps.googleusercontent.com";

    @Override
    @SneakyThrows
    public boolean verify(String body) {
        final JsonNode jsonNode = Json.mapper.readTree(body);
        boolean emailVerified = jsonNode.get(EMAIL_VERIFIED).asBoolean();
        boolean audVerified = Objects.equals(jsonNode.get(AUD).textValue(), AUTH_GOOGLE_AUD_VALUE);
        return emailVerified && audVerified;
    }
}
