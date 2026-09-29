package io.github.fabiocintra.utils.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<List<ErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<ErrorResponse> errors = e
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    String field = fieldError.getField();
                    String error = fieldError.getDefaultMessage();
                    String code = fieldError.getCode();

                    return new ErrorResponse(code,field, error);
                })
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(value = {DataExistsInTheSystemException.class})
    public ResponseEntity<String> handleDataExistsInTheSystemException(DataExistsInTheSystemException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = {ThisIsNotACPFException.class})
    public ResponseEntity<String> handleThisIsNotACPFException(ThisIsNotACPFException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(NotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
