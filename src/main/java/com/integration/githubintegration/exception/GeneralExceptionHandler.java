package com.integration.githubintegration.exception;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> repositoryExceptionResponse(FeignException e) {
        if (e.status() == 404) {
            return ResponseEntity.status(404).body(e.getMessage());
        } else if (e.status() == 301) {
            return ResponseEntity.status(301).body(e.getMessage());
        } else if (e.status() == 403) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
        return ResponseEntity.status(e.status()).body(e.getMessage());
    }

    @ExceptionHandler(BlankArgumentException.class)
    public ResponseEntity<String> repositoryExceptionResponse(BlankArgumentException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}