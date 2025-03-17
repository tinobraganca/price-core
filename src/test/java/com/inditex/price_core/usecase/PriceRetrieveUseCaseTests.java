package com.inditex.price_core.usecase;

import com.inditex.price_core.application.usecase.PriceRetrievalUseCaseImpl;
import com.inditex.price_core.domain.model.Price;
import com.inditex.price_core.infrasctruture.repository.PriceJPARepository;
import com.inditex.price_core.infrasctruture.adapter.db.PriceJPARepositoryAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceRetrieveUseCaseTests {

    @Mock
    private PriceJPARepositoryAdapter priceJPARepositoryAdapter;

    @Mock
    private PriceJPARepository priceJPARepository;

    @InjectMocks
    private PriceRetrievalUseCaseImpl priceRetrievalUseCase;

    private Price price1;
    private Price price2;
    private Price price3;
    private Price price4;

    @BeforeEach
    void setUp() {
        price1 = new Price(1L, LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59), 1, 35455L, 0, BigDecimal.valueOf(35.50), "EUR");
        price2 = new Price(1L, LocalDateTime.of(2020, 6, 14, 15, 0), LocalDateTime.of(2020, 6, 14, 18, 30), 2, 35455L, 1, BigDecimal.valueOf(25.45), "EUR");
        price3 = new Price(1L, LocalDateTime.of(2020, 6, 15, 0, 0), LocalDateTime.of(2020, 6, 15, 11, 0), 3, 35455L, 1, BigDecimal.valueOf(30.50), "EUR");
        price4 = new Price(1L, LocalDateTime.of(2020, 6, 15, 16, 0), LocalDateTime.of(2020, 12, 31, 23, 59), 4, 35455L, 1, BigDecimal.valueOf(38.95), "EUR");
    }

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
        List<Price> applicablePrices = Stream.of(price1, price2, price3, price4)
                .filter(price -> applicationDate.isAfter(price.getStartDate()) && applicationDate.isBefore(price.getEndDate()))
                .toList();

        when(priceJPARepositoryAdapter.findApplicablePrice(1L, 35455L, applicationDate))
                .thenReturn(applicablePrices);

        Optional<Price> result = priceRetrievalUseCase.getPrice(1L, 35455L, applicationDate);

        assertThat(result).isPresent();
        assertThat(result.get().getPrice()).isEqualByComparingTo(expectedPrice);
    }

    @Test
    void shouldReturnEmptyWhenNoApplicablePriceExists() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 20, 10, 0);
        when(priceJPARepositoryAdapter.findApplicablePrice(1L, 35455L, applicationDate))
                .thenReturn(List.of());

        Optional<Price> result = priceRetrievalUseCase.getPrice(1L, 35455L, applicationDate);

        assertThat(result).isEmpty();
    }
}
