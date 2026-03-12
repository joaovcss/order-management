package com.yonix.order_management.infra;

import com.yonix.order_management.exceptions.OrderExceptions.*;
import com.yonix.order_management.exceptions.ProductExceptions.ProductNotFoundException;
import com.yonix.order_management.exceptions.ProductExceptions.InsufficientStockException;
import com.yonix.order_management.exceptions.ProductExceptions.UpdateProductException;
import com.yonix.order_management.exceptions.UserExceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(OrderNotFoundException.class)
    private ResponseEntity<String> orderNotFoundException(OrderNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({OrderCancelledException.class, OrderPaidException.class, OrderShippedException.class, OrderDeliveredException.class})
    private ResponseEntity<String> orderStateException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(InsufficientStockException.class)
    private ResponseEntity<String> insufficientStockException(InsufficientStockException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UpdateProductException.class)
    private ResponseEntity<String> updateProductException(UpdateProductException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<String> userNotFoundException(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}
