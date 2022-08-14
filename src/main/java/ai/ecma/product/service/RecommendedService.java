package ai.ecma.product.service;

import ai.ecma.lib.payload.ApiResult;
import ai.ecma.product.payload.RecommendedReqDto;

import java.util.UUID;

public interface RecommendedService {


    ApiResult<?> getRecommended(UUID recommendedId);

    ApiResult<?> create(RecommendedReqDto recommendedReqDto);
}
