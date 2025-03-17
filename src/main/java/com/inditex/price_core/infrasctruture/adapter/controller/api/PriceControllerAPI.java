package com.inditex.price_core.infrasctruture.adapter.controller.api;

import com.inditex.price_core.infrasctruture.adapter.controller.exception.InvalidDateFormatException;
import com.inditex.price_core.infrasctruture.dto.ErrorResponse;
import com.inditex.price_core.infrasctruture.dto.PriceDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Price API", description = "API for retrieving product prices")
public interface PriceControllerAPI {

    @Operation(description = "Retrieves the applicable price for a given product, brand, and date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PriceDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<PriceDto> getPrice(@RequestParam Long brandId,
                                      @RequestParam Long productId,
                                      @RequestParam String applicationDate);
}
