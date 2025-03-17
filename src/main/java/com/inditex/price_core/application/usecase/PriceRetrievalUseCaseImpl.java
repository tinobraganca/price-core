package com.inditex.price_core.application.usecase;

import com.inditex.price_core.domain.model.Price;
import com.inditex.price_core.domain.port.in.PriceRetrievalUseCase;
import com.inditex.price_core.domain.port.out.PriceRepository;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

public class PriceRetrievalUseCaseImpl implements PriceRetrievalUseCase {

    private final PriceRepository priceRepository;

    public PriceRetrievalUseCaseImpl(PriceRepository repository) {
        this.priceRepository = repository;
    }

    @Override
    public Optional<Price> getPrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        return priceRepository.findApplicablePrice(brandId, productId, applicationDate)
                .stream()
                .max(Comparator.comparing(Price::getPriority));
    }
}