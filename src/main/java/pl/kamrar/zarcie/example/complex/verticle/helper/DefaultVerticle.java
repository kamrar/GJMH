package pl.kamrar.zarcie.example.complex.verticle.helper;

import io.vertx.core.Vertx;
import io.vertx.rxjava.core.AbstractVerticle;

import javax.annotation.PostConstruct;

public abstract class DefaultVerticle extends AbstractVerticle {

    @PostConstruct
    void init() {
        Vertx.vertx().deployVerticle(this);
    }

    public abstract void start() throws Exception;
}
