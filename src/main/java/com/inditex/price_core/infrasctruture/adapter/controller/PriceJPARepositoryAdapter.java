package com.inditex.price_core.infrasctruture.adapter.controller;

import com.inditex.price_core.domain.model.Price;
import com.inditex.price_core.domain.port.out.PriceRepository;
import com.inditex.price_core.infrasctruture.entity.PriceEntity;
import com.inditex.price_core.infrasctruture.repository.PriceJPARepository;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PriceJPARepositoryAdapter implements PriceRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceJPARepositoryAdapter.class);

    private final PriceJPARepository priceJPARepository;

    public PriceJPARepositoryAdapter(PriceJPARepository priceJPARepository) {
        this.priceJPARepository = priceJPARepository;
    }

    @Override
    public List<Price> findApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        LOGGER.warn("Fetching price for Brand: {}, Product: {}, Date: {}", brandId, productId, applicationDate);

        return priceJPARepository.findApplicablePrice(brandId, productId, applicationDate).stream()
                .map(PriceEntity::fromEntity).toList();
    }
}
