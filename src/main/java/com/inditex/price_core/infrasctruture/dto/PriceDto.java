package com.inditex.price_core.infrasctruture.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inditex.price_core.domain.model.Price;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceDto {
    private Long brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceList;
    private Long productId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private BigDecimal price;
    private String currency;


    public PriceDto(){}

    public PriceDto(Long brandId, LocalDateTime startDate, LocalDateTime endDate, Integer priceList, Long productId, BigDecimal price, String currency) {
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.productId = productId;
        this.price = price;
        this.currency = currency;
    }

    public static PriceDto fromDomain(Price price) {
        return new PriceDto(
                price.getBrandId(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPriceList(),
                price.getProductId(),
                price.getPrice(),
                price.getCurrency()
        );
    }

    public Long getBrandId() { return brandId; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public Integer getPriceList() { return priceList; }
    public Long getProductId() { return productId; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }
}