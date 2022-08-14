package ai.ecma.product.service;


import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.resp.ProductRespDto;

import java.util.List;
import java.util.UUID;

public interface FavouriteService {

    ApiResult<ProductRespDto> getOne(UUID id);

    ApiResult<List<ProductRespDto>> getAllByUser();

    ApiResult<?> add(UUID productId);

    ApiResult<?> deleteOne(UUID id);

    ApiResult<?> deleteAll();

}
