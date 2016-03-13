package pl.kamrar.gjmh.model.persisted.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kamrar.gjmh.model.persisted.entity.User;

//TODO change to mongo
public interface UserRepository extends CrudRepository<User, Long> {
}
