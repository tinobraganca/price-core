package com.inditex.price_core.domain.port.out;

import com.inditex.price_core.domain.model.Price;
import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository {
    List<Price> findApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate);
}