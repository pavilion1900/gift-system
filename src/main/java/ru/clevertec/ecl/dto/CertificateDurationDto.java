package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateDurationDto {

    @Positive
    private Integer duration;

    private LocalDateTime lastUpdateDate;
}
