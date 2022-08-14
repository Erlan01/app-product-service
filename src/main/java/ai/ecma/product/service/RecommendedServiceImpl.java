package ai.ecma.product.service;

import ai.ecma.lib.entity.Category;
import ai.ecma.lib.entity.Recommended;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.repository.CategoryRepository;
import ai.ecma.lib.repository.RecommendedRepository;
import ai.ecma.product.common.MessageService;
import ai.ecma.product.exception.RestException;
import ai.ecma.product.payload.RecommendedReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecommendedServiceImpl implements RecommendedService {

    private final RecommendedRepository recommendedRepository;

    private final CategoryRepository categoryRepository;


    @Override
    public ApiResult<?> getRecommended(UUID recommendedId) {
        Recommended recommended = recommendedRepository.findById(recommendedId).orElseThrow(() -> RestException.notFound("RECOMMENDED"));
        return ApiResult.successResponse(recommended);
    }

    @Override
    public ApiResult<?> create(RecommendedReqDto recommendedReqDto) {
        Category toCategory = categoryRepository.findById(recommendedReqDto.getToCategoryId()).orElseThrow(() -> RestException.notFound("CATEGORY"));
        if (recommendedReqDto.getFromCategoryId().size() > 3)
            throw RestException.restThrow(MessageService.getMessage("CATEGORY_MUST_BE_THREE"), HttpStatus.BAD_REQUEST);
        List<Category> fromCategoryList = categoryRepository.findAllById(recommendedReqDto.getFromCategoryId());
        Recommended recommended = new Recommended();
        for (Category category : fromCategoryList) {
            recommended.setToCategory(toCategory);
            recommended.setFromCategory(category);
        }
        recommendedRepository.save(recommended);
        return ApiResult.successResponse(MessageService.successSave("RECOMMENDED"));
    }
}
