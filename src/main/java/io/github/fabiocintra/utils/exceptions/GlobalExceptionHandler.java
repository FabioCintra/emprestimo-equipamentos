package io.github.fabiocintra.utils.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {DataExistsInTheSystemException.class})
    public ResponseEntity<String> handleDataExistsInTheSystemException(DataExistsInTheSystemException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = {ThisIsNotACPFException.class})
    public ResponseEntity<String> handleThisIsNotACPFException(ThisIsNotACPFException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
