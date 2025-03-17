package com.inditex.price_core.controller;

import com.inditex.price_core.application.service.PriceService;
import com.inditex.price_core.domain.model.Price;
import com.inditex.price_core.infrasctruture.adapter.controller.GlobalExceptionHandler;
import com.inditex.price_core.infrasctruture.adapter.controller.PriceController;
import com.inditex.price_core.infrasctruture.adapter.controller.exception.InvalidDateFormatException;
import com.inditex.price_core.infrasctruture.utils.DateFormatterUtils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PriceControllerTests {

    @Mock
    private PriceService priceService;

    @InjectMocks
    private PriceController priceController;

    private MockMvc mockMvc;


    @BeforeEach
    public void setupMockMvc() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(priceController).setControllerAdvice(GlobalExceptionHandler.class).build();
    }

    private void mockPriceResponse(String date, BigDecimal price, int priceList) {
        LocalDateTime dateTime = DateFormatterUtils.parseApplicationDate(date);
        when(priceService.getPrice(1L, 35455L, dateTime)).thenReturn(Optional.of(
                new Price(1L, dateTime, dateTime.plusHours(2), priceList, 35455L,1, price, "EUR")
        ));
    }

    @Test
    void testPriceControllerGetPrice() throws Exception {
        String validDate = "2020-06-15 14:30:00";

        when(priceService.getPrice(1L, 12345L, DateFormatterUtils.parseApplicationDate(validDate)))
                .thenReturn(Optional.of(
                        new Price(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(1), 1, 12345L, 1, BigDecimal.valueOf(99.99), "EUR")
                ));

        mockMvc.perform(get("/prices")
                        .param("brandId", "1")
                        .param("productId", "12345")
                        .param("applicationDate", validDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.brandId").value(1L));
    }

    @Test
    void testDateUtilValidFormat() {
        String validDate = "2023-06-15 14:30:00";
        LocalDateTime parsedDate = DateFormatterUtils.parseApplicationDate(validDate);
        assertThat(parsedDate).isNotNull();
    }


    @Test
    public void shouldRetrieveNotFoundException() throws Exception {
        String validDate = "2023-06-15 14:30:00";

        mockMvc.perform(get("/prices")
                        .param("brandId", "1")
                        .param("productId", "12345")
                        .param("applicationDate", validDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(DateFormatterUtils.getNotFoundPrice()));
    }

    @Test
    public void shouldRetrieveErrorTypeException() throws Exception {
        String validDate = "2023-06-15 14:30:00";

        mockMvc.perform(get("/prices")
                        .param("brandId", "AA")
                        .param("productId", "12345")
                        .param("applicationDate", validDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(DateFormatterUtils.getErrortypeMessage()));
    }

    @Test
    public void shouldRetrieveDataErrorTypeException() throws Exception {
        String invalidDate = "2023-06-15AAA14:30:00";

        mockMvc.perform(get("/prices")
                        .param("brandId", "1")
                        .param("productId", "12345")
                        .param("applicationDate", invalidDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(DateFormatterUtils.getFormatDateMessage()))
        ;

    }

    @Test
    void shouldThrowDateUtilInvalidFormat() {
        String invalidDate = "2023-06-15-14.30.00";
        assertThatThrownBy(() -> DateFormatterUtils.parseApplicationDate(invalidDate))
                .isInstanceOf(InvalidDateFormatException.class)
                .hasMessage(DateFormatterUtils.getFormatDateMessage());
    }

    @ParameterizedTest(name = "[executed - {index} - {0}]")
    @CsvSource({
            "2020-06-14 10:00:00, 35.50, 1",
            "2020-06-14 16:00:00, 25.45, 2",
            "2020-06-14 21:00:00, 35.50, 1",
            "2020-06-15 10:00:00, 30.50, 3",
            "2020-06-16 21:00:00, 38.95, 4"
    })
    void testPriceParameterized(String date, BigDecimal expectedPrice, int priceList) throws Exception {
        setupMockMvc();
        mockPriceResponse(date, expectedPrice, priceList);

        mockMvc.perform(get("/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("applicationDate", date)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(expectedPrice));
    }

}
