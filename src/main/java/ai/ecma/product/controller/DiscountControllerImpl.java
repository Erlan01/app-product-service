package ai.ecma.product.controller;

import ai.ecma.lib.payload.ApiResult;
import ai.ecma.product.payload.DiscountDto;
import ai.ecma.product.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DiscountControllerImpl implements DiscountController {

    private final DiscountService discountService;

    @Override
    public ApiResult<?> getAll(Integer page, Integer size) {
        return discountService.getAll(page, size);
    }

    @Override
    public ApiResult<?> get(Long id) {
        return discountService.get(id);
    }

    @Override
    public ApiResult<?> create(DiscountDto discountDto) {
        return discountService.create(discountDto);
    }

    @Override
    public ApiResult<?> edit(Long id, DiscountDto discountDto) {
        return discountService.edit(id, discountDto);
    }

    @Override
    public ApiResult<?> delete(Long id) {
        return discountService.delete(id);
    }
}
