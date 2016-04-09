package pl.kamrar.gjmh.model.entity;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String image;

    @Override
    public User isAuthorised(String authority, Handler<AsyncResult<Boolean>> resultHandler) {
        return null;
    }

    @Override
    public User clearCache() {
        return null;
    }

    @Override
    public JsonObject principal() {
        return null;
    }

    @Override
    public void setAuthProvider(AuthProvider authProvider) {

    }
}
