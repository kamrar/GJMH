package pl.kamrar.gjmh.verticle.api.auth;

public interface AuthVerifier {
    boolean verify(String body);
}
