package ai.ecma.product.controller;

import ai.ecma.lib.payload.ApiResult;
import ai.ecma.product.payload.RecommendedReqDto;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static ai.ecma.product.controller.RecommendedController.RECOMMENDED_CONTROLLER;
import static ai.ecma.product.utils.AppConstant.BASE_PATH;

@RequestMapping(RECOMMENDED_CONTROLLER)
public interface RecommendedController {

    String RECOMMENDED_CONTROLLER = BASE_PATH + "/recommended";


    @GetMapping("/recommendedId")
    ApiResult<?> getRecommended(@PathVariable UUID recommendedId);


    @PostMapping()
    ApiResult<?> create(@RequestBody RecommendedReqDto recommendedReqDto);
}
