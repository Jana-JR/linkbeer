package org.jana.urlshortnersb.config;

import org.jana.urlshortnersb.exceptions.BlockedUrlException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BlockedUrlException.class)
    public ResponseEntity<?> handleBlockedUrlException(BlockedUrlException ex, WebRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Unsafe URL");
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("reason", ex.getReason());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "An error occurred");
        errorDetails.put("message", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex, WebRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Bad Request");
        errorDetails.put("message", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
