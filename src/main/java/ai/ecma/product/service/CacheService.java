package ai.ecma.product.service;

import ai.ecma.lib.payload.UserDto;
import ai.ecma.product.exception.RestException;
import ai.ecma.product.feign.AuthFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * This class not documented :(
 *
 * @author Muhammad Mo'minov
 * @since 17.02.2022
 */
@Service
@RequiredArgsConstructor
public class CacheService {
    private final AuthFeign authFeign;

    @Cacheable(value = "users", key = "#token")
    public UserDto getUserByToken(String token) {
        return authFeign.checkAuth(token).orElseThrow(RestException::forbidden);
    }

}
