package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@Builder
public class TagDto {

    private Integer id;

    @NotBlank
    private String name;
}
