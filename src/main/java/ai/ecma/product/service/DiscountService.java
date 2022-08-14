package ai.ecma.product.service;


import ai.ecma.lib.entity.Discount;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.CustomPage;
import ai.ecma.product.payload.DiscountDto;
import org.springframework.data.domain.Page;

public interface DiscountService {
    ApiResult<?> getAll(Integer page, Integer size);

    ApiResult<?> get(Long id);

    ApiResult<?> create(DiscountDto discountDto);

    ApiResult<?> edit(Long id, DiscountDto discountDto);

    ApiResult<?> delete(Long id);

    CustomPage<DiscountDto> makeCustomPage(Page<Discount> page);
}
