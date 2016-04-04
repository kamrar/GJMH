package pl.kamrar.gjmh.model;

import lombok.Data;

@Data
public class Product {
    private String name;
    private Double price;

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}
