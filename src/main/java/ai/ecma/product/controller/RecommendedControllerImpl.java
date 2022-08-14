package ai.ecma.product.controller;

import ai.ecma.lib.payload.ApiResult;
import ai.ecma.product.payload.RecommendedReqDto;
import ai.ecma.product.service.RecommendedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RecommendedControllerImpl implements RecommendedController {

    private final RecommendedService recommendedService;


    @Override
    public ApiResult<?> getRecommended(UUID recommendedId) {
        return recommendedService.getRecommended(recommendedId);
    }

    @Override
    public ApiResult<?> create(RecommendedReqDto recommendedReqDto) {
        return recommendedService.create(recommendedReqDto);
    }
}
