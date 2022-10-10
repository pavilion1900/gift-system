package ru.clevertec.ecl.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {

    private String errorMessage;
    private Integer errorCode;
}
