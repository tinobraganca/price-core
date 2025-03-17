package com.inditex.price_core.domain.port.in;

import com.inditex.price_core.domain.model.Price;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRetrievalUseCase {
    Optional<Price> getPrice(Long brandId, Long productId, LocalDateTime applicationDate);
}
