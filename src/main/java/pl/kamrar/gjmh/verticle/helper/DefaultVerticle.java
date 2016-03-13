package pl.kamrar.gjmh.verticle.helper;

import io.vertx.core.Vertx;
import io.vertx.rxjava.core.AbstractVerticle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public abstract class DefaultVerticle extends AbstractVerticle {

    @PostConstruct
    void init() {
        Vertx.vertx().deployVerticle(this);
    }

    public abstract void start() throws Exception;
}
