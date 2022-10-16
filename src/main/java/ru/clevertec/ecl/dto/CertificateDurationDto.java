package ru.clevertec.ecl.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@Builder
public class CertificateDurationDto {

    @Positive
    @NotNull
    private Integer duration;

    private LocalDateTime lastUpdateDate;
}
