/**
 * 
 */
package com.pruebaTecnica.ecommerce.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pruebaTecnica.ecommerce.exception.ProductNotFoundException;

/**
 * 
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	 private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	    @ExceptionHandler(ProductNotFoundException.class)
	    public ResponseEntity<Object> handleProductNotFound(ProductNotFoundException ex) {
	        logger.error("Product not found: {}", ex.getMessage());  // Loguea el error con SLF4J
	        return new ResponseEntity<>(new ErrorResponse("Product Not Found", ex.getMessage()), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<Object> handleGeneralException(Exception ex) {
	        logger.error("Internal server error: {}", ex.getMessage(), ex);  // Loguea el error con detalles
	        return new ResponseEntity<>(new ErrorResponse("Internal Server Error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
