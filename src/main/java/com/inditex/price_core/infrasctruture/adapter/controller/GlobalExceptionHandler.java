package com.inditex.price_core.infrasctruture.adapter.controller;

import com.inditex.price_core.infrasctruture.adapter.controller.exception.InvalidDateFormatException;
import com.inditex.price_core.infrasctruture.adapter.controller.exception.PriceNotFoundException;
import com.inditex.price_core.infrasctruture.dto.ErrorResponse;
import com.inditex.price_core.infrasctruture.utils.DateFormatterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDateFormatException(InvalidDateFormatException e) {
        LOG.error("method=handleInvalidDateFormatException message={}", e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), DateFormatterUtils.getFormatDateMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

    }
    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePriceNotFoundeException(PriceNotFoundException e) {
        LOG.error("method=handlePriceNotFoundeException message={}", e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), DateFormatterUtils.getNotFoundPrice(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        String errorMessage = "Invalid value for parameter '" + paramName + "'. Expected type: " + ex.getRequiredType().getSimpleName();
        LOG.error("method=handleTypeMismatchException message={}", errorMessage, ex);
        ErrorResponse errorResponse = new ErrorResponse(errorMessage, DateFormatterUtils.getErrortypeMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        LOG.error("method=handleGeneralException message=Unexpected error occurred: ", e);
        ErrorResponse errorResponse = new ErrorResponse("Internal server error occurred.", DateFormatterUtils.getInternalErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}