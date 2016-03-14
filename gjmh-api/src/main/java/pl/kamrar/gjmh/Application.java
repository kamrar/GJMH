package pl.kamrar.gjmh;

import io.vertx.core.Vertx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.kamrar.gjmh.verticle.config.GlobalRouter;

@SpringBootApplication
public class Application {

    public Application() {
        deployVerticles();
    }

    /**
     * Deployment of verticles that has to be accessible before spring init
     */
    private void deployVerticles() {
        Vertx.vertx().deployVerticle(new GlobalRouter());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
