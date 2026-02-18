package com.yonix.order_management.infra;

import com.yonix.order_management.exceptions.OrderExceptions.*;
import com.yonix.order_management.exceptions.ProductExceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OrderStatusException.class)
    private ResponseEntity<String> orderStatusException(OrderStatusException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    private ResponseEntity<String> productNotFoundException(ProductNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<String> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception){
        return ResponseEntity
                .badRequest()
                .body("invalid id format");
    }

}
