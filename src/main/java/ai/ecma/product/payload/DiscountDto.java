package ai.ecma.product.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto {

    private Long id;

    @NotBlank
    private String name;

    @Positive
    private double percent;

    @NotNull
    private Long expireTime;

    @NotNull
    private Long startTime;

    @NotNull
    private Double price;


    public DiscountDto(Long id, String name, double percent, Double price, long startTime, long expireTime) {
        this.id = id;
        this.name = name;
        this.percent = percent;
        this.price = price;
        this.startTime = startTime;
        this.expireTime = expireTime;
    }
}
