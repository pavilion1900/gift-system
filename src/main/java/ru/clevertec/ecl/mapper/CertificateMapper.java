package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.entity.Certificate;

import java.time.LocalDateTime;

@Mapper(imports = LocalDateTime.class)
public interface CertificateMapper {

    CertificateDto toDto(Certificate certificate);

    @Mapping(target = "createDate", defaultExpression = "java(LocalDateTime.now())")
    @Mapping(target = "lastUpdateDate", defaultExpression = "java(LocalDateTime.now())")
    Certificate toEntity(CertificateDto certificateDto);
}
