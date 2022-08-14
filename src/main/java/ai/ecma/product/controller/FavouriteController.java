package ai.ecma.product.controller;

import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.resp.ProductRespDto;
import ai.ecma.product.utils.AppConstant;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(FavouriteController.FAVOURITE_CONTROLLER)
public interface FavouriteController {

    String FAVOURITE_CONTROLLER = AppConstant.BASE_PATH + "/favourite";

    @GetMapping("/{id}")
    ApiResult<ProductRespDto> getOne(@PathVariable UUID id);

    @GetMapping
    ApiResult<List<ProductRespDto>> getAllByUser();

    @PostMapping("/{productId}")
    ApiResult<?> add(@PathVariable UUID productId);

    @DeleteMapping("/{id}")
    ApiResult<?> deleteOne(@PathVariable UUID id);

    @DeleteMapping
    ApiResult<?> deleteAll();

}
