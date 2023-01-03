package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificatePriceDto {

    @Positive
    private BigDecimal price;

    private LocalDateTime lastUpdateDate;
}
