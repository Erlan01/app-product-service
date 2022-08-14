package ai.ecma.product.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedReqDto {

    private Long toCategoryId;

    private List<Long> fromCategoryId;
}
