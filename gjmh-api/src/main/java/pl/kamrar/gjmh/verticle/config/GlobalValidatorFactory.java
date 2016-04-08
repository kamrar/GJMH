package pl.kamrar.gjmh.verticle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

@Configuration
public class GlobalValidatorFactory {

    @Bean
    ValidatorFactory localValidatorBean(){
        return Validation.buildDefaultValidatorFactory();
    }
}
