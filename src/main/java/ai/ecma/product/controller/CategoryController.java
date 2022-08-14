package ai.ecma.product.controller;


import ai.ecma.lib.payload.ApiResult;
import ai.ecma.product.payload.CategoryDto;
import ai.ecma.product.utils.AppConstant;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static ai.ecma.product.utils.AppConstant.DEFAULT_PAGE_NUMBER;
import static ai.ecma.product.utils.AppConstant.DEFAULT_PAGE_SIZE;

@RequestMapping(CategoryController.CATEGORY_CONTROLLER)
public interface CategoryController {

    String CATEGORY_CONTROLLER = AppConstant.BASE_PATH + "/category";

    @GetMapping()
    ApiResult<?> getAll(@RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer page,
                        @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size);


    @GetMapping("{id}")
    ApiResult<?> get(@PathVariable Long id);

    @PostMapping()
    ApiResult<?> create(@RequestBody @Valid CategoryDto categoryDto);

    @PutMapping("{id}")
    ApiResult<?> edit(@PathVariable Long id, @RequestBody @Valid CategoryDto categoryDto);


    @DeleteMapping("{id}")
    ApiResult<?> delete(@PathVariable Long id);

    @DeleteMapping()
    ApiResult<?> deletedAll();
}
