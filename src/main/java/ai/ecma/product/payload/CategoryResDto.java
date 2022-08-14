package ai.ecma.product.payload;

import ai.ecma.lib.entity.Attachment;
import ai.ecma.lib.entity.Category;
import ai.ecma.lib.entity.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResDto {

    private Long id;

    private String name;

    private Category parent;

    private Attachment photo;

    private Discount discount;
}
