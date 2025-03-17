package com.inditex.price_core.infrasctruture.adapter.controller;

import com.inditex.price_core.application.service.PriceService;
import com.inditex.price_core.infrasctruture.adapter.controller.api.PriceControllerAPI;
import com.inditex.price_core.infrasctruture.dto.PriceDto;
import com.inditex.price_core.infrasctruture.utils.DateFormatterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prices")
public class PriceController implements PriceControllerAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(PriceController.class);

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    @Override
    public ResponseEntity<PriceDto> getPrice(@RequestParam Long brandId,
                                             @RequestParam Long productId,
                                             @RequestParam String applicationDate) {
        LOGGER.info("Getting price for Brand: {}, Product: {}, Date: {}", brandId, productId, applicationDate);

        PriceDto ret = priceService.getPrice(brandId, productId, DateFormatterUtils.parseApplicationDate(applicationDate))
                .map(PriceDto::fromDomain).orElse(new PriceDto());
        LOGGER.info("Retrieved price for Brand: {}, Product: {}, Date: {}", brandId, productId, applicationDate);

        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }
}