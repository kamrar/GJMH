package pl.kamrar.gjmh.verticle.api.auth;

import io.vertx.rxjava.core.buffer.Buffer;

public enum OAuth2ProviderType {
    GOOGLE(new GoogleAuthVerifier(), "www.googleapis.com", "/oauth2/v3/tokeninfo?id_token=");

    private AuthVerifier authVerifier;
    private String host;
    private String uri;

    OAuth2ProviderType(AuthVerifier authVerifier, String host, String uri) {
        this.authVerifier = authVerifier;
        this.host = host;
        this.uri = uri;
    }

    public boolean verify(Buffer body) {
        return authVerifier.verify(body.toString());
    }

    public String host() {
        return host;
    }

    public String uri() {
        return uri;
    }

    public static OAuth2ProviderType provider(String name) {
        return OAuth2ProviderType.valueOf(name.toUpperCase());
    }
}
