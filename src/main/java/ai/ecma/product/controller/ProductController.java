package ai.ecma.product.controller;

import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.CustomPage;
import ai.ecma.lib.payload.req.ProductDiscountDto;
import ai.ecma.lib.payload.req.ProductReqDto;
import ai.ecma.lib.payload.resp.ProductRespDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static ai.ecma.product.utils.AppConstant.*;

/**
 * @author Murtazayev Muhammad
 * @since 23.01.2022
 */
@RequestMapping(ProductController.PRODUCT_CONTROLLER)
public interface ProductController {
    String PRODUCT_CONTROLLER = BASE_PATH + "/product";

    @GetMapping
    ApiResult<CustomPage<ProductRespDto>> get(@RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer page,
                                              @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size);

    @GetMapping("/category/{categoryId}")
    ApiResult<CustomPage<ProductRespDto>> getByCategory(@RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer page,
                                                        @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size,
                                                        @PathVariable Long categoryId);

    @GetMapping("/discount/{discountId}")
    ApiResult<CustomPage<ProductRespDto>> getByDiscount(@RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer page,
                                                        @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size,
                                                        @PathVariable Long discountId);

    @GetMapping("/{id}")
    ApiResult<ProductRespDto> get(@PathVariable UUID id);

    @PostMapping("/create")
    ApiResult<?> create(@RequestBody @Valid ProductReqDto dto);

    @PostMapping("/attach-discount")
    ApiResult<?> attachDiscount(@RequestBody @Valid ProductDiscountDto dto);

    @PostMapping("/detach-discount")
    ApiResult<?> detachDiscount(@RequestBody @Valid ProductDiscountDto dto);

    @PutMapping("/edit/{id}")
    ApiResult<?> edit(@PathVariable UUID id, @RequestBody @Valid ProductReqDto dto);

    @DeleteMapping("/delete/{id}")
    ApiResult<?> delete(@PathVariable UUID id);
}
