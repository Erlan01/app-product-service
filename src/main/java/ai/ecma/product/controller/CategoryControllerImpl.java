package ai.ecma.product.controller;

import ai.ecma.lib.payload.ApiResult;
import ai.ecma.product.payload.CategoryDto;
import ai.ecma.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;


    @Override
    public ApiResult<?> getAll(Integer page, Integer size) {
        return categoryService.getAll(page, size);
    }

    @Override
    public ApiResult<?> get(Long id) {
        return categoryService.get(id);
    }

    @Override
    public ApiResult<?> create(CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @Override
    public ApiResult<?> edit(Long id, CategoryDto categoryDto) {
        return categoryService.edit(id, categoryDto);
    }

    @Override
    public ApiResult<?> delete(Long id) {
        return categoryService.delete(id);
    }

    @Override
    public ApiResult<?> deletedAll() {
        return categoryService.deletedAll();
    }
}
