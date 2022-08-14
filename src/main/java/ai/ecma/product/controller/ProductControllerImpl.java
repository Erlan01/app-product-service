package ai.ecma.product.controller;

import ai.ecma.lib.enums.PermissionEnum;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.CustomPage;
import ai.ecma.lib.payload.req.ProductDiscountDto;
import ai.ecma.lib.payload.req.ProductReqDto;
import ai.ecma.lib.payload.resp.ProductRespDto;
import ai.ecma.product.aop.CheckAuth;
import ai.ecma.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Murtazayev Muhammad
 * @since 23.01.2022
 */
@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    @Override
    public ApiResult<CustomPage<ProductRespDto>> get(Integer page, Integer size) {
        return productService.get(page, size);
    }

    @Override
    public ApiResult<CustomPage<ProductRespDto>> getByCategory(Integer page, Integer size, Long categoryId) {
        return productService.getByCategory(page, size, categoryId);
    }

    @Override
    public ApiResult<CustomPage<ProductRespDto>> getByDiscount(Integer page, Integer size, Long discountId) {
        return productService.getByDiscount(page, size, discountId);
    }

    @Override
    public ApiResult<ProductRespDto> get(UUID id) {
        return productService.get(id);
    }

    @Override
    @CheckAuth(permissions = PermissionEnum.ADD_PRODUCT)
    public ApiResult<?> create(ProductReqDto dto) {
        return productService.create(dto);
    }

    @Override
    public ApiResult<?> attachDiscount(ProductDiscountDto dto) {
        return productService.attachDiscount(dto);
    }

    @Override
    public ApiResult<?> detachDiscount(ProductDiscountDto dto) {
        return productService.detachDiscount(dto);
    }

    @Override
    public ApiResult<?> edit(UUID id, ProductReqDto dto) {
        return productService.edit(id, dto);
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return productService.delete(id);
    }
}
