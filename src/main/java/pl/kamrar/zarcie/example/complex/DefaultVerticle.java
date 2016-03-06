package pl.kamrar.zarcie.example.complex;

import io.vertx.core.Vertx;
import io.vertx.rxjava.core.AbstractVerticle;

import javax.annotation.PostConstruct;

public abstract class DefaultVerticle extends AbstractVerticle {

    @PostConstruct
    void init() {
        Vertx.vertx().deployVerticle(this);
    }
}
