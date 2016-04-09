package pl.kamrar.gjmh.model.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kamrar.gjmh.model.entity.UserProfile;

//TODO change to mongo
public interface UserRepository extends CrudRepository<UserProfile, Long> {
    UserProfile findByEmail(String email);
}
