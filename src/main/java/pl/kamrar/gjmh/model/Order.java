package pl.kamrar.gjmh.model;

import lombok.Data;
import pl.kamrar.gjmh.model.enumerate.OrderStatus;
import pl.kamrar.gjmh.model.enumerate.OrderSubStatus;

import java.time.LocalDateTime;

@Data
public class Order {
    private long id;
    private String name;
    private String local;
    private String website;
    private LocalDateTime orderDateTime;
    private LocalDateTime createDateTime;
    private OrderStatus orderStatus;
    private OrderSubStatus orderSubStatus;
}
