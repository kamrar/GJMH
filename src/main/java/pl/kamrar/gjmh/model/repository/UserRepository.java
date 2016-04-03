package pl.kamrar.gjmh.model.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kamrar.gjmh.model.entity.User;

//TODO change to mongo
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
