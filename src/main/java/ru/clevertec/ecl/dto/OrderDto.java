package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"cost", "userId", "certificateId"})
@Builder
public class OrderDto {

    private Integer id;
    private BigDecimal cost;
    private LocalDateTime purchaseDate;

    @Positive
    @NotNull
    private Integer userId;

    @Positive
    @NotNull
    private Integer certificateId;
}
