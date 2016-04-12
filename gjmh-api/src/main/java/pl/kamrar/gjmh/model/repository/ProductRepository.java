package pl.kamrar.gjmh.model.repository;

import org.springframework.stereotype.Repository;
import pl.kamrar.gjmh.model.Order;
import pl.kamrar.gjmh.model.Product;

@Repository
public class ProductRepository extends MongoRepository<Product>{}
