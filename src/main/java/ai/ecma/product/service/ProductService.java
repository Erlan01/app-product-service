package ai.ecma.product.service;

import ai.ecma.lib.entity.Product;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.CustomPage;
import ai.ecma.lib.payload.req.ProductDiscountDto;
import ai.ecma.lib.payload.req.ProductReqDto;
import ai.ecma.lib.payload.resp.ProductRespDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * @author Murtazayev Muhammad
 * @since 23.01.2022
 */
public interface ProductService {
    ApiResult<CustomPage<ProductRespDto>> get(Integer page, Integer size);

    ApiResult<CustomPage<ProductRespDto>> getByCategory(Integer page, Integer size, Long categoryId);

    ApiResult<CustomPage<ProductRespDto>> getByDiscount(Integer page, Integer size, Long discountId);

    ApiResult<ProductRespDto> get(UUID id);

    ApiResult<?> create(ProductReqDto dto);

    ApiResult<?> attachDiscount(ProductDiscountDto dto);

    ApiResult<?> detachDiscount(ProductDiscountDto dto);

    ApiResult<?> edit(UUID id, ProductReqDto dto);

    ApiResult<?> delete(UUID id);

    CustomPage<ProductRespDto> makeCustomPage(Page<Product> products);
}
