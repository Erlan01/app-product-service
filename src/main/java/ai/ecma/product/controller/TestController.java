package ai.ecma.product.controller;

import ai.ecma.lib.enums.PermissionEnum;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.UserDto;
import ai.ecma.product.aop.CheckAuth;
import ai.ecma.product.feign.AuthFeign;
import ai.ecma.product.utils.AppConstant;
import ai.ecma.product.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class not documented :(
 *
 * @author Muhammad Mo'minov
 * @since 20.01.2022
 */
@RestController
@RequestMapping(AppConstant.BASE_PATH + "/test")
@RequiredArgsConstructor
public class TestController {
    private final AuthFeign authFeign;

    @GetMapping("/public")
    public String publicApi() {
        return "Welcome to Public API";
    }

    @GetMapping("/private")
    @CheckAuth
    public String privateApi() {
        return "Welcome to Private API";
    }

    @GetMapping("/add-product")
    @CheckAuth(permissions = {PermissionEnum.ADD_PRODUCT, PermissionEnum.TEST})
    public ApiResult<?> byPermission() {
        UserDto currentUser = CommonUtils.getCurrentUser();
        return ApiResult.successResponse(currentUser);
    }

    @GetMapping("/add-promotion")
    @CheckAuth(permissions = {PermissionEnum.ADD_PROMOTION})
    public String addPromotion() {
        return "Promotion successfully saved!";
    }


    @GetMapping
    public String test(@RequestHeader String authorization) {
        System.out.println("Start.....");
        ApiResult<UserDto> result = authFeign.checkAuth(authorization);
        System.out.println("End.....");
        return result.toString();
    }

    @GetMapping("/color")
    public ApiResult<?> test() {
        List<String> colorList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Random randomGenerator = new Random();
            int red = randomGenerator.nextInt(256);
            int green = randomGenerator.nextInt(256);
            int blue = randomGenerator.nextInt(256);

            colorList.add(String.format("#%02x%02x%02x", red, green, blue));
        }
        return ApiResult.successResponse(colorList);
    }
}
