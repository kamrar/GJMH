package pl.kamrar.gjmh.model;

import lombok.Data;
import pl.kamrar.gjmh.model.enumerate.OrderStatus;
import pl.kamrar.gjmh.model.enumerate.OrderSubStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private String name;
    private String local;
    private String website;
    private LocalDateTime orderDateTime;
    private LocalDateTime createDateTime;
    private OrderStatus orderStatus;
    private OrderSubStatus orderSubStatus;
    private List<Product> productList;

    public Order(String name, String local, String website, LocalDateTime orderDateTime, LocalDateTime createDateTime, OrderStatus orderStatus, OrderSubStatus orderSubStatus){
        this.name = name;
        this.local = local;
        this.website = website;
        this.orderDateTime = orderDateTime;
        this.createDateTime = createDateTime;
        this.orderStatus = orderStatus;
        this.orderSubStatus = orderSubStatus;
    }
}
