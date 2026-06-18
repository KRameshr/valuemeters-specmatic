package com.banking.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAccountNotFound(AccountNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 400 business
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Map<String, Object>> handleBalance(InsufficientBalanceException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegal(IllegalArgumentException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // VALIDATION ERROR (Specmatic important fix)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {

        String msg = "Validation failed";

        if (ex.getBindingResult().getFieldError() != null) {
            msg = ex.getBindingResult().getFieldError().getDefaultMessage();
        }

        return build(HttpStatus.BAD_REQUEST, msg);
    }

    // JSON malformed
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJson(HttpMessageNotReadableException ex) {
        return build(HttpStatus.BAD_REQUEST, "Malformed JSON request");
    }

    // path param type mismatch
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleType(MethodArgumentTypeMismatchException ex) {
        return build(HttpStatus.BAD_REQUEST, "Invalid parameter type");
    }

    // missing params
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissing(MissingServletRequestParameterException ex) {
        return build(HttpStatus.BAD_REQUEST, "Missing required parameter");
    }

    // fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    // STRICT SPECMATIC FORMAT
    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message) {

        Map<String, Object> error = new HashMap<>();
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        error.put("status", status.value());

        return new ResponseEntity<>(error, status);
    }
}
