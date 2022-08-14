package ai.ecma.product.service;

import ai.ecma.lib.entity.Attachment;
import ai.ecma.lib.entity.Category;
import ai.ecma.lib.entity.Discount;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.CustomPage;
import ai.ecma.lib.repository.AttachmentRepository;
import ai.ecma.lib.repository.CategoryRepository;
import ai.ecma.lib.repository.DiscountRepository;
import ai.ecma.product.common.MessageService;
import ai.ecma.product.exception.RestException;
import ai.ecma.product.payload.CategoryDto;
import ai.ecma.product.payload.CategoryResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final DiscountRepository discountRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public ApiResult<?> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("name")));
        Page<Category> all = categoryRepository.findAll(pageable);
        return ApiResult.successResponse(makeCustomPage(all));
    }

    @Override
    public ApiResult<?> get(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> RestException.notFound("CATEGORY"));
        return ApiResult.successResponse(new CategoryResDto(category.getId(), category.getName(), category.getParent(), category.getPhoto(), category.getDiscount()));
    }

    @Override
    public ApiResult<?> create(CategoryDto categoryDto) {
        Category parentCategory = null;
        if (categoryDto.getParent() != null) {
            parentCategory = categoryRepository.findById(categoryDto.getParent()).orElseThrow(() -> RestException.notFound("CATEGORY"));
            if (categoryRepository.existsByName(categoryDto.getName()))
                return new ApiResult<>(RestException.alreadyExists("CATEGORY"));
        }
        Attachment photo = attachmentRepository.findById(categoryDto.getPhoto()).orElseThrow(() -> RestException.notFound("ATTACHMENT"));
        Discount discount = null;
        if (categoryDto.getDiscountId() != null)
            discount = discountRepository.findById(categoryDto.getDiscountId()).orElseThrow(() -> RestException.notFound("DISCOUNT"));

        categoryRepository.save(Category.builder()
                .name(categoryDto.getName())
                .parent(parentCategory)
                .photo(photo)
                .discount(discount)
                .build());
        return ApiResult.successResponse(MessageService.successSave("CATEGORY"));
    }

    @Override
    public ApiResult<?> edit(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> RestException.notFound("CATEGORY"));
        Category parentCategory = null;
        if (categoryDto.getParent() != null) {
            parentCategory = categoryRepository.findById(categoryDto.getParent()).orElseThrow(() -> RestException.notFound("CATEGORY"));
            if (categoryRepository.existsByName(categoryDto.getName()))
                return new ApiResult<>(RestException.alreadyExists("CATEGORY"));
        }
        Attachment photo = attachmentRepository.findById(categoryDto.getPhoto()).orElseThrow(() -> RestException.notFound("ATTACHMENT"));
        Discount discount = null;
        if (categoryDto.getDiscountId() != null)
            discount = discountRepository.findById(categoryDto.getDiscountId()).orElseThrow(() -> RestException.notFound("DISCOUNT"));
        category.setName(categoryDto.getName() != null ? categoryDto.getName() : category.getName());
        category.setParent(parentCategory);
        category.setPhoto(photo);
        category.setDiscount(discount);
        categoryRepository.save(category);
        return ApiResult.successResponse(MessageService.successEdit("CATEGORY"));
    }

    @Override
    public ApiResult<?> delete(Long id) {
        try {
            categoryRepository.deleteById(id);
            return ApiResult.successResponse(MessageService.successDelete("CATEGORY"));
        } catch (Exception e) {
            throw RestException.notFound("CATEGORY");
        }
    }

    @Override
    public ApiResult<?> deletedAll() {
        categoryRepository.deleteAll();
        return ApiResult.successResponse(MessageService.successDelete("CATEGORY"));
    }

    @Override
    public CustomPage<CategoryResDto> makeCustomPage(Page<Category> page) {
        return new CustomPage<>(
                page.getContent().stream().map(category ->
                        new CategoryResDto(
                                category.getId(),
                                category.getName(),
                                category.getParent(),
                                category.getPhoto(),
                                category.getDiscount())).collect(Collectors.toList()),
                page.getNumberOfElements(),
                page.getNumber(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSize()
        );
    }
}
