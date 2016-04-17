package pl.kamrar.gjmh.model.repository;

import org.springframework.stereotype.Repository;
import pl.kamrar.gjmh.model.Order;

@Repository
public class OrderRepository extends MongoRepository<Order> {
}
