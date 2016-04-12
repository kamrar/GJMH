package pl.kamrar.gjmh.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product extends Model {

    private String _id;

    @NotNull(message = "value can not be null")
    private String name;

    @NotNull(message = "value can not be null")
    private Double price;

    @NotNull(message = "value can not be null")
    private String order_id;

    public static Product product(String json) {
        return (Product) Product.build(json, Product.class);
    }
}