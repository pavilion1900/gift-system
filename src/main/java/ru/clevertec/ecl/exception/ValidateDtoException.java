package ru.clevertec.ecl.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.validation.ValidationException;

@Getter
public class ValidateDtoException extends ValidationException {

    private final HttpStatus status = HttpStatus.BAD_REQUEST;
    private final Integer code = 40001;

    public ValidateDtoException(String message) {
        super(message);
    }
}
