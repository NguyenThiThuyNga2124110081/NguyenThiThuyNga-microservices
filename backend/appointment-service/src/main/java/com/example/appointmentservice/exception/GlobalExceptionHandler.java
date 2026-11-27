package com.example.appointmentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý lỗi không tìm thấy dữ liệu nội bộ
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", ex.getMessage()));
    }

    // Xử lý lỗi không tìm thấy dữ liệu tại Service phụ thuộc (Lab 03 - Mục 7)
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<?> handleRemoteNotFound(HttpClientErrorException.NotFound ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Không tìm thấy dữ liệu tại Service phụ thuộc"));
    }

    // Xử lý lỗi không thể kết nối đến Service phụ thuộc (Lab 03 - Mục 7)
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<?> handleServiceUnavailable(ResourceAccessException ex) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("message", "Không thể kết nối đến Service phụ thuộc"));
    }

    // Xử lý lỗi Service phụ thuộc trả về lỗi không mong đợi (Lab 03 - Mục 7)
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<?> handleRemoteServerError(HttpServerErrorException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("message", "Service phụ thuộc trả về lỗi không mong đợi"));
    }

    // Xử lý lỗi Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "message", "Dữ liệu không hợp lệ",
                        "errors", errors
                ));
    }

    // Xử lý lỗi logic/tham số không hợp lệ
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of("message", ex.getMessage()));
    }

    // Xử lý các lỗi ngoại lệ khác
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Đã xảy ra lỗi hệ thống: " + ex.getMessage()));
    }
}
