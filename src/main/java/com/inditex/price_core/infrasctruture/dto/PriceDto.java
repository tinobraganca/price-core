package com.inditex.price_core.infrasctruture.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.inditex.price_core.domain.model.Price;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceDto {
    private Long brandId;
    private Long productId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private BigDecimal price;
    private String currency;

    public PriceDto(){}

    public PriceDto(Long brandId, Long productId, BigDecimal price, String currency) {
        this.brandId = brandId;
        this.productId = productId;
        this.price = price;
        this.currency = currency;
    }

    public static PriceDto fromDomain(Price price) {
        return new PriceDto(
                price.getBrandId(),
                price.getProductId(),
                price.getPrice(),
                price.getCurrency()
        );
    }

    public Long getBrandId() { return brandId; }
    public Long getProductId() { return productId; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }
}