//package com.banking.exception;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    // 404 - Account not found
//    @ExceptionHandler(AccountNotFoundException.class)
//    public ResponseEntity<Map<String, Object>> handleAccountNotFound(AccountNotFoundException ex) {
//        return buildResponse(HttpStatus.NOT_FOUND, 404, "Not Found", ex.getMessage());
//    }
//
//    // 400 - Insufficient balance (business rule)
//    @ExceptionHandler(InsufficientBalanceException.class)
//    public ResponseEntity<Map<String, Object>> handleInsufficientBalance(InsufficientBalanceException ex) {
//        return buildResponse(HttpStatus.BAD_REQUEST, 400, "Bad Request", ex.getMessage());
//    }
//
//    // 400 - Invalid JSON body
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<Map<String, Object>> handleInvalidJson(HttpMessageNotReadableException ex) {
//        return buildResponse(HttpStatus.BAD_REQUEST, 400, "Bad Request", "Invalid request body format");
//    }
//
//    // 400 - Invalid arguments
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
//        return buildResponse(HttpStatus.BAD_REQUEST, 400, "Bad Request", ex.getMessage());
//    }
//
//    // ⚠️ IMPORTANT FIX:
//    // Do NOT catch RuntimeException globally (BREAKS Specmatic 200 tests)
//    // REMOVE THIS:
//    // @ExceptionHandler(RuntimeException.class)
//
//    // fallback ONLY for unexpected errors (safe)
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
//        return buildResponse(HttpStatus.BAD_REQUEST, 400, "Bad Request", "Something went wrong");
//    }
//
//    private ResponseEntity<Map<String, Object>> buildResponse(
//            HttpStatus status,
//            int statusCode,
//            String errorType,
//            String message) {
//
//        Map<String, Object> error = new HashMap<>();
//        error.put("timestamp", LocalDateTime.now());
//        error.put("status", statusCode);
//        error.put("error", errorType);
//        error.put("message", message);
//
//        return new ResponseEntity<>(error, status);
//    }
//}





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

    // ✅ VALIDATION ERROR (Specmatic important fix)
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

    // ✅ STRICT SPECMATIC FORMAT
    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message) {

        Map<String, Object> error = new HashMap<>();
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        error.put("status", status.value());

        return new ResponseEntity<>(error, status);
    }
}
