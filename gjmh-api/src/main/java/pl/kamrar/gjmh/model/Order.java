package pl.kamrar.gjmh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kamrar.gjmh.model.enumerate.OrderStatus;
import pl.kamrar.gjmh.model.enumerate.OrderSubStatus;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    @NotNull
    private String name;

    private String local;

    private String website;

    private LocalDateTime orderDateTime;

    private LocalDateTime createDateTime;

    private OrderStatus orderStatus;

    private OrderSubStatus orderSubStatus;

    private List<Product> productList;
}
