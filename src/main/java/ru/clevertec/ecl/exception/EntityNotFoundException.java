package ru.clevertec.ecl.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final HttpStatus status = HttpStatus.NOT_FOUND;
    private final Integer code = 40401;

    public EntityNotFoundException(String message) {
        super(message);
    }
}
