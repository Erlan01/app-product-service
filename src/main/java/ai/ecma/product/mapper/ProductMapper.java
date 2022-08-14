package ai.ecma.product.mapper;

import ai.ecma.lib.entity.Product;
import ai.ecma.lib.payload.resp.ProductRespDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "photoId", source = "photo.id")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "discountId", source = "discount.id")
    ProductRespDto toProductRespDto(Product product);
}