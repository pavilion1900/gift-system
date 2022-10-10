package ru.clevertec.ecl.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.ecl.exception.ErrorMessage;
import ru.clevertec.ecl.exception.EntityNotFoundException;

@RestControllerAdvice
public class CertificateExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleException(EntityNotFoundException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(ErrorMessage.builder()
                        .errorMessage(exception.getMessage())
                        .errorCode(exception.getCode())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.builder()
                        .errorMessage(exception.getMessage())
                        .errorCode(40001)
                        .build());
    }
}
