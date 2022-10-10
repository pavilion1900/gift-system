package ru.clevertec.ecl.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.CertificateDurationDto;
import ru.clevertec.ecl.dto.CertificatePriceDto;
import ru.clevertec.ecl.entity.Certificate;

import java.time.LocalDateTime;

@Mapper(uses = {TagMapper.class}, imports = LocalDateTime.class)
public interface CertificateMapper {

    @Mapping(source = "certificate.tags", target = "dtoTags")
    CertificateDto toDto(Certificate certificate);

    @Mapping(source = "certificateDto.dtoTags", target = "tags")
    @Mapping(target = "createDate", defaultExpression = "java(LocalDateTime.now())")
    @Mapping(target = "lastUpdateDate", defaultExpression = "java(LocalDateTime.now())")
    Certificate toEntity(CertificateDto certificateDto);

    @Mapping(target = "lastUpdateDate", expression = "java(LocalDateTime.now())")
    void updateDto(CertificateDto source, @MappingTarget CertificateDto target);

    @Mapping(target = "lastUpdateDate", expression = "java(LocalDateTime.now())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePriceDto(CertificatePriceDto source, @MappingTarget CertificateDto target);

    @Mapping(target = "lastUpdateDate", expression = "java(LocalDateTime.now())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDurationDto(CertificateDurationDto source, @MappingTarget CertificateDto target);
}
