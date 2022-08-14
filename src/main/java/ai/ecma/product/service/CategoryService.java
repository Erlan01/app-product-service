package ai.ecma.product.service;

import ai.ecma.lib.entity.Category;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.CustomPage;
import ai.ecma.product.payload.CategoryDto;
import ai.ecma.product.payload.CategoryResDto;
import org.springframework.data.domain.Page;

public interface CategoryService {
    ApiResult<?> getAll(Integer page, Integer size);

    ApiResult<?> get(Long id);

    ApiResult<?> create(CategoryDto categoryDto);

    CustomPage<CategoryResDto> makeCustomPage(Page<Category> page);

    ApiResult<?> edit(Long id, CategoryDto categoryDto);

    ApiResult<?> delete(Long id);

    ApiResult<?> deletedAll();
}
