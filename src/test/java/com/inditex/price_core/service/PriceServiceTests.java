package com.inditex.price_core.service;

import com.inditex.price_core.application.service.PriceService;
import com.inditex.price_core.application.usecase.PriceRetrievalUseCaseImpl;
import com.inditex.price_core.domain.model.Price;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(value = MockitoExtension.class)
public class PriceServiceTests {

    @Mock
    private PriceRetrievalUseCaseImpl priceRetrievalUseCase;

    @InjectMocks
    private PriceService priceService;
    
    @ParameterizedTest(name = "[executed - {index} - {0}]")
    @CsvSource({
            "2020-06-14T10:00:00, 35.50",
            "2020-06-14T16:00:00, 25.45",
            "2020-06-14T21:00:00, 35.50",
            "2020-06-15T10:00:00, 30.50",
            "2020-06-16T21:00:00, 38.95"
    })
    void shouldReturnCorrectPriceForGivenDates(String date, BigDecimal expectedPrice) {

        LocalDateTime applicationDate = LocalDateTime.parse(date);

        Price expectedPriceObject = new Price(
                1L,
                applicationDate.minusHours(1),
                applicationDate.plusHours(1),
                1,
                35455L,
                1,
                expectedPrice,
                "EUR"
        );

        when(priceRetrievalUseCase.getPrice(1L, 35455L, applicationDate))
                .thenReturn(Optional.of(expectedPriceObject));


        Optional<Price> result = priceService.getPrice(1L, 35455L, applicationDate);

        assertThat(result).isPresent();
        assertThat(result.get().getPrice()).isEqualByComparingTo(expectedPrice);
    }

    @Test
    void shouldReturnEmptyWhenNoApplicablePriceExists() {

        when(priceRetrievalUseCase.getPrice(anyLong(), anyLong(), any()))
                .thenReturn(Optional.empty());

        Optional<Price> result = priceService.getPrice(1L, 35455L, LocalDateTime.now());

        assertThat(result).isEmpty();
    }
}