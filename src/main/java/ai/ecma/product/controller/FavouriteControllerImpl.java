package ai.ecma.product.controller;

import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.resp.ProductRespDto;
import ai.ecma.product.aop.CheckAuth;
import ai.ecma.product.service.FavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FavouriteControllerImpl implements FavouriteController {
    private final FavouriteService favouriteService;

    @Override
    @CheckAuth
    public ApiResult<ProductRespDto> getOne(UUID id) {
        return favouriteService.getOne(id);
    }

    @Override
    @CheckAuth
    public ApiResult<List<ProductRespDto>> getAllByUser() {
        return favouriteService.getAllByUser();
    }

    @Override
    @CheckAuth
    public ApiResult<?> add(UUID productId) {
        return favouriteService.add(productId);
    }

    @Override
    @CheckAuth
    public ApiResult<?> deleteOne(UUID id) {
        return favouriteService.deleteOne(id);
    }

    @Override
    @CheckAuth
    public ApiResult<?> deleteAll() {
        return favouriteService.deleteAll();
    }
}
