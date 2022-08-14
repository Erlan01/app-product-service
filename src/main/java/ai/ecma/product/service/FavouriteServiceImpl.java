package ai.ecma.product.service;

import ai.ecma.lib.entity.Favourite;
import ai.ecma.lib.entity.Product;
import ai.ecma.lib.entity.User;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.UserDto;
import ai.ecma.lib.payload.resp.ProductRespDto;
import ai.ecma.lib.repository.FavouriteRepository;
import ai.ecma.lib.repository.ProductRepository;
import ai.ecma.lib.repository.UserRepository;
import ai.ecma.product.common.MessageService;
import ai.ecma.product.exception.RestException;
import ai.ecma.product.mapper.ProductMapper;
import ai.ecma.product.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    @Override
    public ApiResult<ProductRespDto> getOne(UUID id) {
        UserDto currentUser = CommonUtils.getCurrentUser();
        Favourite favourite = favouriteRepository.findByIdAndUserId(id, currentUser.getId()).orElseThrow(() -> RestException.notFound("FAVOURITE"));

        Product product = favourite.getProduct();
        return ApiResult.successResponse(productMapper.toProductRespDto(product));
    }

    @Override
    public ApiResult<List<ProductRespDto>> getAllByUser() {
        UserDto currentUser = CommonUtils.getCurrentUser();
        List<Favourite> favouriteList = favouriteRepository.findAllByUserId(currentUser.getId());
        List<ProductRespDto> result = favouriteList.stream().map(o -> productMapper.toProductRespDto(o.getProduct())).collect(Collectors.toList());
        return ApiResult.successResponse(result);
    }

    @Override
    public ApiResult<?> add(UUID productId) {
        UserDto currentUser = CommonUtils.getCurrentUser();
        User user = userRepository.findById(currentUser.getId()).orElseThrow(RestException::forbidden);
        Product product = productRepository.findByIdAndActiveIsTrue(productId).orElseThrow(() -> RestException.notFound("PRODUCT"));

        Favourite favourite = new Favourite();
        favourite.setProduct(product);
        favourite.setUser(user);
        favouriteRepository.save(favourite);
        return ApiResult.successResponse(MessageService.successSave("FAVOURITE"));
    }

    @Override
    public ApiResult<?> deleteOne(UUID id) {
        try {
            favouriteRepository.deleteById(id);
            return ApiResult.successResponse(MessageService.successDelete("FAVOURITE"));
        } catch (Exception e) {
            throw RestException.notFound("FAVOURITE");
        }
    }

    @Override
    public ApiResult<?> deleteAll() {
        UserDto currentUser = CommonUtils.getCurrentUser();
        favouriteRepository.deleteAllByUserId(currentUser.getId());
        return ApiResult.successResponse(MessageService.successDelete("FAVOURITE"));
    }
}
