package com.inditex.price_core.application.service;

import com.inditex.price_core.domain.model.Price;
import com.inditex.price_core.domain.port.in.PriceRetrievalUseCase;
import java.time.LocalDateTime;
import java.util.Optional;

public class PriceService implements PriceRetrievalUseCase {

    private final PriceRetrievalUseCase priceRetrievalUseCase;

    public PriceService(PriceRetrievalUseCase priceRetrievalUseCase) {
        this.priceRetrievalUseCase = priceRetrievalUseCase;
    }

    @Override
    public Optional<Price> getPrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        return priceRetrievalUseCase.getPrice(brandId, productId, applicationDate);
    }
}
