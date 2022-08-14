package ai.ecma.product.controller;


import ai.ecma.lib.payload.ApiResult;
import ai.ecma.product.payload.DiscountDto;
import ai.ecma.product.utils.AppConstant;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static ai.ecma.product.utils.AppConstant.DEFAULT_PAGE_NUMBER;
import static ai.ecma.product.utils.AppConstant.DEFAULT_PAGE_SIZE;

@RequestMapping(DiscountController.DISCOUNT_CONTROLLER)
public interface DiscountController {

    String DISCOUNT_CONTROLLER = AppConstant.BASE_PATH + "/discount";

    @GetMapping()
    ApiResult<?> getAll(@RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer page,
                        @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size);

    @GetMapping("{id}")
    ApiResult<?> get(@PathVariable Long id);

    @PostMapping()
    ApiResult<?> create(@RequestBody @Valid DiscountDto discountDto);

    @PutMapping("{id}")
    ApiResult<?> edit(@PathVariable Long id, @RequestBody @Valid DiscountDto discountDto);

    @DeleteMapping("{id}")
    ApiResult<?> delete(@PathVariable Long id);
}
