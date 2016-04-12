package pl.kamrar.gjmh.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kamrar.gjmh.model.enumerate.OrderStatus;
import pl.kamrar.gjmh.model.enumerate.OrderSubStatus;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order extends Model {

    private String _id;

    @NotNull(message = "value can not be null")
    @Size(min = 1, max = 100, message = "value should be in the range from 1 to 100")
    private String name;

    @NotNull(message = "value can not be null")
    @Size(min = 1, max = 100, message = "value should be in the range from 1 to 100")
    private String premises;

    @NotNull(message = "value can not be null")
    @Size(min = 1, max = 100, message = "value should be in the range from 1 to 100")
    private String website;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @NotNull(message = "value can not be null")
    private LocalDateTime order_date_time;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @NotNull(message = "value can not be null")
    private LocalDateTime create_date_time;

    private OrderStatus order_status;

    private OrderSubStatus order_sub_status;

    private List<Product> product_list;

    public static Order order(String json) {
        return (Order) Order.build(json, Order.class);
    }
}
