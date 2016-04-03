package pl.kamrar.gjmh.model;

import lombok.Data;

@Data
public class Product {
    private long id;
    private String name;

    public Product(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
