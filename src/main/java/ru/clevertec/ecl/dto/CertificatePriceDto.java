package ru.clevertec.ecl.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CertificatePriceDto {

    @Positive
    @NotNull
    private BigDecimal price;

    private LocalDateTime lastUpdateDate;
}
