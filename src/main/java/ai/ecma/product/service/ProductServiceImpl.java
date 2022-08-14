package ai.ecma.product.service;

import ai.ecma.lib.entity.Attachment;
import ai.ecma.lib.entity.Category;
import ai.ecma.lib.entity.Discount;
import ai.ecma.lib.entity.Product;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.CustomPage;
import ai.ecma.lib.payload.req.ProductDiscountDto;
import ai.ecma.lib.payload.req.ProductReqDto;
import ai.ecma.lib.payload.resp.ProductRespDto;
import ai.ecma.lib.repository.AttachmentRepository;
import ai.ecma.lib.repository.CategoryRepository;
import ai.ecma.lib.repository.DiscountRepository;
import ai.ecma.lib.repository.ProductRepository;
import ai.ecma.product.common.MessageService;
import ai.ecma.product.exception.RestException;
import ai.ecma.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Murtazayev Muhammad
 * @since 23.01.2022
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final ProductMapper productMapper;

    @Override
    public ApiResult<CustomPage<ProductRespDto>> get(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.asc("name")));
        Page<Product> productPage = productRepository.findAll(pageRequest);
        return ApiResult.successResponse(makeCustomPage(productPage));
    }

    @Override
    public ApiResult<CustomPage<ProductRespDto>> getByCategory(Integer page, Integer size, Long categoryId) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.asc("name")));
        Page<Product> productPage = productRepository.findAllByCategoryId(pageRequest, categoryId);
        return ApiResult.successResponse(makeCustomPage(productPage));
    }

    @Override
    public ApiResult<CustomPage<ProductRespDto>> getByDiscount(Integer page, Integer size, Long discountId) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.asc("name")));
        Page<Product> productPage = productRepository.findAllByDiscountId(pageRequest, discountId);
        return ApiResult.successResponse(makeCustomPage(productPage));
    }

    @Override
    public ApiResult<ProductRespDto> get(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> RestException.notFound("PRODUCT"));
        return ApiResult.successResponse(productMapper.toProductRespDto(product));
    }

    @Override
    public ApiResult<?> create(ProductReqDto dto) {
        Discount discount = null;
        Attachment photo = null;
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> RestException.notFound("CATEGORY"));
        if (Objects.nonNull(dto.getDiscountId()))
            discount = discountRepository.findById(dto.getDiscountId()).orElseThrow(() -> RestException.notFound("DISCOUNT"));
        if (Objects.nonNull(dto.getPhotoId())) {
            photo = attachmentRepository.findById(dto.getPhotoId()).orElseThrow(() -> RestException.notFound("ATTACHMENT"));
        }
        Product product = productRepository.save(new Product(dto.getName(), dto.getPrice(), photo, category, discount, dto.getDescription(), dto.isActive()));
        return ApiResult.successResponse(productMapper.toProductRespDto(product));
    }

    @Override
    public ApiResult<?> attachDiscount(ProductDiscountDto dto) {
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> RestException.notFound("PRODUCT"));
        Discount discount = discountRepository.findById(dto.getDiscountId()).orElseThrow(() -> RestException.notFound("DISCOUNT"));
        product.setDiscount(discount);
        product = productRepository.save(product);
        return ApiResult.successResponse(product, MessageService.getMessage("DISCOUNT_ATTACHED"));
    }

    @Override
    public ApiResult<?> detachDiscount(ProductDiscountDto dto) {
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> RestException.notFound("PRODUCT"));
        product.setDiscount(null);
        return ApiResult.successResponse(product, MessageService.getMessage("DISCOUNT_DETACHED"));
    }

    @Override
    public ApiResult<?> edit(UUID id, ProductReqDto dto) {
        Product product = productRepository.findById(id).orElseThrow(() -> RestException.notFound("PRODUCT"));
        Discount discount = null;
        Attachment photo = null;
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> RestException.notFound("CATEGORY"));
        if (Objects.nonNull(dto.getDiscountId()))
            discount = discountRepository.findById(dto.getDiscountId()).orElseThrow(() -> RestException.notFound("DISCOUNT"));
        if (Objects.nonNull(dto.getPhotoId())) {
            photo = attachmentRepository.findById(dto.getPhotoId()).orElseThrow(() -> RestException.notFound("ATTACHMENT"));
        }
        product.setName(dto.getName());
        product.setActive(dto.isActive());
        product.setCategory(category);
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setDiscount(discount);
        product.setPhoto(photo);
        product = productRepository.save(product);
        return ApiResult.successResponse(productMapper.toProductRespDto(product));
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        try {
            productRepository.deleteById(id);
            return ApiResult.successResponse(MessageService.successDelete("PRODUCT"));
        } catch (Exception e) {
            throw RestException.notFound("PRODUCT");
        }
    }

    @Override
    public CustomPage<ProductRespDto> makeCustomPage(Page<Product> products) {
        return new CustomPage<>(
                products.getContent().stream().map(productMapper::toProductRespDto).collect(Collectors.toList()),
                products.getNumberOfElements(),
                products.getNumber(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.getSize()
        );
    }
}
