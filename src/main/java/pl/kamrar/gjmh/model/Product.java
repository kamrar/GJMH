package pl.kamrar.gjmh.model;

import lombok.Data;

@Data
public class Product {
    private long id;
    private String name;
    private Double price;

    public Product(long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
