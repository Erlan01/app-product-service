package ai.ecma.product.service;

import ai.ecma.lib.entity.Discount;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.CustomPage;
import ai.ecma.lib.repository.DiscountRepository;
import ai.ecma.product.common.MessageService;
import ai.ecma.product.exception.RestException;
import ai.ecma.product.payload.DiscountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    @Override
    public ApiResult<?> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("name")));
        Page<Discount> all = discountRepository.findAll(pageable);
        return ApiResult.successResponse(makeCustomPage(all));
    }

    @Override
    public ApiResult<?> get(Long id) {
        Discount discount = discountRepository.findById(id).orElseThrow(() -> RestException.notFound("DISCOUNT"));
        return ApiResult.successResponse(new DiscountDto(discount.getId(), discount.getName(), discount.getPercent(), discount.getPrice(), discount.getStartTime().getTime(), discount.getExpireTime().getTime()));
    }

    @Override
    public ApiResult<?> create(DiscountDto discountDto) {
        if (discountRepository.existsByName(discountDto.getName()))
            return new ApiResult<>(RestException.alreadyExists("DISCOUNT"));
        Timestamp startTime = new Timestamp(discountDto.getStartTime());
        Timestamp expireTime = new Timestamp(discountDto.getExpireTime());

        Discount discount = new Discount();
        discount.setName(discountDto.getName());
        discount.setPercent(discountDto.getPercent());
        discount.setPrice(discountDto.getPrice());
        discount.setStartTime(startTime);
        discount.setExpireTime(expireTime);
        discount.setActive(true);
        discountRepository.save(discount);
        return ApiResult.successResponse(MessageService.successSave("DISCOUNT"));
    }

    @Override
    public ApiResult<?> edit(Long id, DiscountDto discountDto) {
        Discount discount = discountRepository.findById(id).orElseThrow(() -> RestException.notFound("DISCOUNT"));
        Timestamp startTime = new Timestamp(discountDto.getStartTime());
        Timestamp expireTime = new Timestamp(discountDto.getExpireTime());
        discount.setName(discountDto.getName() != null ? discountDto.getName() : discount.getName());
        discount.setPercent(discountDto.getPercent());
        discount.setPrice(discountDto.getPrice() != null ? discountDto.getPrice() : discount.getPrice());
        discount.setStartTime(startTime);
        discount.setExpireTime(expireTime);
        discountRepository.save(discount);
        return ApiResult.successResponse(MessageService.successEdit("DISCOUNT"));
    }

    @Override
    public ApiResult<?> delete(Long id) {
        try {
            Discount discount = discountRepository.findById(id).orElseThrow(() -> RestException.notFound("DISCOUNT"));
            discount.setDeleted(false);
            discount.setActive(false);
            discountRepository.save(discount);
            return new ApiResult<>(MessageService.successDelete("DISCOUNT"));
        } catch (EmptyResultDataAccessException e) {
            return new ApiResult<>(MessageService.cannotDelete("DISCOUNT"));
        }
    }

    @Override
    public CustomPage<DiscountDto> makeCustomPage(Page<Discount> page) {
        return new CustomPage<>(
                page.getContent().stream().map(
                                discount -> new DiscountDto(discount.getId(), discount.getName(), discount.getPercent(), discount.getPrice(), discount.getStartTime().getTime(), discount.getExpireTime().getTime()))
                        .collect(Collectors.toList()),
                page.getNumberOfElements(),
                page.getNumber(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSize());
    }
}
