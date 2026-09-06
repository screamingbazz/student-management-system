package com.kv.studentmanagement.exception;

import com.kv.studentmanagement.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntimeException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.warn(
                "Business rule violation occurred at path [{}]: {}",
                request.getRequestURI(),
                ex.getMessage()
        );

        ApiErrorResponse errorBody = new ApiErrorResponse(
                Instant.now().toString(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGlobalException(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error(
                "CRITICAL system error caught at path [{}]: ",
                request.getRequestURI(),
                ex
        );

        ApiErrorResponse errorBody = new ApiErrorResponse(
                Instant.now().toString(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred. Please contact system support.",
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        FieldError fieldError =
                ex.getBindingResult().getFieldError();

        String customErrorMessage =
                (fieldError != null)
                        ? fieldError.getDefaultMessage()
                        : "Invalid input payload attributes.";

        log.warn(
                "Input validation failed at path [{}]: {}",
                request.getRequestURI(),
                customErrorMessage
        );

        ApiErrorResponse errorBody = new ApiErrorResponse(
                Instant.now().toString(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                customErrorMessage,
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }
}
