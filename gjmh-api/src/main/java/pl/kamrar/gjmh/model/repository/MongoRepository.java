package pl.kamrar.gjmh.model.repository;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.rxjava.ext.mongo.MongoClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class MongoRepository<T> {

    public static String ID = "_id";

    @Autowired
    private MongoClient mongoClient;

    @Getter
    @Setter
    private String parametrizedClassSimpleName = ((Class<T>)
            ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0]).getSimpleName().toLowerCase();

    public Observable<String> insert(JsonObject jsonObject) {
        return mongoClient.insertObservable(parametrizedClassSimpleName, jsonObject);
    }

    public Observable<Void> update(String id, JsonObject jsonObject) {
        return mongoClient.updateObservable(parametrizedClassSimpleName, new JsonObject().put(ID, id), new JsonObject().put("$set", jsonObject));
    }

    public Observable<JsonObject> find(String id) {
        return mongoClient.findOneObservable(parametrizedClassSimpleName, new JsonObject().put(ID, id), null);
    }

    public Observable<List<JsonObject>> findAll(JsonObject findOptions) {
        return mongoClient.findWithOptionsObservable(parametrizedClassSimpleName, null, new FindOptions(findOptions));
    }

    public Observable<Void> remove(String id) {
        String typeParamName = parametrizedClassSimpleName;
        return mongoClient.removeObservable(typeParamName, new JsonObject().put(ID, id));
    }

}
