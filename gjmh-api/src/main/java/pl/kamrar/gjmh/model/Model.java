package pl.kamrar.gjmh.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

public abstract class Model<T> {

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    protected static final ValidatorFactory VALIDATION_FACTORY = Validation.buildDefaultValidatorFactory();

    @SneakyThrows
    public static Model build(String json, Class clazz) {
        return (Model) OBJECT_MAPPER.readValue(json, clazz);
    }

    public Set<ConstraintViolation<Model<T>>> valid() {
        return VALIDATION_FACTORY.getValidator().validate(this);
    }
}
