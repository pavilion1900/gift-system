package ru.clevertec.ecl.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.entity.Certificate;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoWithId1;
import static ru.clevertec.ecl.util.CertificateUtil.certificateWithId1;

class CertificateMapperTest {

    private final CertificateMapper mapper = Mappers.getMapper(CertificateMapper.class);

    @Test
    void checkToDto() {
        CertificateDto actual = mapper.toDto(certificateWithId1());
        assertThat(actual).isEqualTo(certificateDtoWithId1());
    }

    @Test
    void checkToEntity() {
        Certificate actual = mapper.toEntity(certificateDtoWithId1());
        assertThat(actual).isEqualTo(certificateWithId1());
    }
}
