package pl.kamrar.gjmh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.kamrar.gjmh.model.enumerate.OrderStatus;
import pl.kamrar.gjmh.model.enumerate.OrderSubStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Order {
    private String name;
    private String local;
    private String website;
    private LocalDateTime orderDateTime;
    private LocalDateTime createDateTime;
    private OrderStatus orderStatus;
    private OrderSubStatus orderSubStatus;
    private List<Product> productList;
}
