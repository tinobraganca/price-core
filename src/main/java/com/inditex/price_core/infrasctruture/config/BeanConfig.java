package com.inditex.price_core.infrasctruture.config;

import com.inditex.price_core.application.service.PriceService;
import com.inditex.price_core.application.usecase.PriceRetrievalUseCaseImpl;
import com.inditex.price_core.domain.port.out.PriceRepository;
import com.inditex.price_core.domain.port.in.PriceRetrievalUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public PriceService priceService(PriceRepository priceRepository){
        return new PriceService(new PriceRetrievalUseCaseImpl(priceRepository));

    }

    @Bean
    public PriceRetrievalUseCase priceRetrievalService(PriceRepository priceQueryRepository) {
        return new PriceRetrievalUseCaseImpl(priceQueryRepository);
    }

}
