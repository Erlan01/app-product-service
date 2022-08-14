package ai.ecma.product.payload;

import ai.ecma.lib.entity.Product;
import ai.ecma.lib.entity.User;
import lombok.Data;


@Data
public class FavouriteDto {

    private Product product;
    private User user;

}
