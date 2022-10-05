package ru.clevertec.ecl.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.integration.IntegrationTestBase;
import ru.clevertec.ecl.service.CertificateService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoForSaveWithId;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoForSaveWithoutId;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoForUpdateDuration;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoForUpdateWithoutId;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoUpdatedDuration;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoWithId1;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoWithoutId;
import static ru.clevertec.ecl.util.CertificateUtil.dtoCertificates;
import static ru.clevertec.ecl.util.CertificateUtil.pageable;

@RequiredArgsConstructor
public class CertificateServiceImplTest extends IntegrationTestBase {

    private final CertificateService service;

    @Test
    void checkFindAll() {
        List<CertificateDto> actual = service.findAll(pageable());
        assertEquals(dtoCertificates(), actual);
    }

    @Test
    void checkFindAllByPartOfName() {
        List<CertificateDto> actual = service.findAllBy("fir", null, pageable());
        assertEquals(Collections.singletonList(certificateDtoWithId1()), actual);
    }

    @Test
    void checkFindAllByPartOfDescription() {
        List<CertificateDto> actual = service.findAllBy(null, "desc", pageable());
        assertEquals(dtoCertificates(), actual);
    }

    @Test
    void checkFindAllByTagName() {
        List<CertificateDto> actual = service.findAllByTagName("new", pageable());
        assertEquals(dtoCertificates(), actual);
    }

    @Test
    void checkFindByIdIfCertificateIdExist() {
        CertificateDto actual = service.findById(1);
        assertEquals(certificateDtoWithId1(), actual);
    }

    @Test
    void throwExceptionIfCertificateIdNotExist() {
        assertThrows(EntityNotFoundException.class, () -> service.findById(10));
    }

    @Test
    void checkFindByNameIfCertificateNameExist() {
        CertificateDto actual = service.findByName("first");
        assertEquals(certificateDtoWithId1(), actual);
    }

    @Test
    void checkFindByNameIfCertificateNameExist2() {
        CertificateDto actual = service.findByName("FiRsT");
        assertEquals(certificateDtoWithId1(), actual);
    }

    @Test
    void throwExceptionIfCertificateNameNotExist() {
        assertThrows(EntityNotFoundException.class, () -> service.findByName("short333"));
    }

    @Test
    void checkSaveIfCertificateHasUniqueName() {
        CertificateDto actual = service.save(certificateDtoForSaveWithoutId());
        assertEquals(certificateDtoForSaveWithId(), actual);
    }

    @Test
    void checkUpdateIfCertificateHasUniqueIdAndUniqueName() {
        CertificateDto actual = service.update(1, certificateDtoForUpdateDuration());
        assertEquals(certificateDtoUpdatedDuration(), actual);
    }

    @Test
    void throwExceptionByUpdateIfCertificateIdNotExist() {
        assertThrows(EntityNotFoundException.class,
                () -> service.update(10, certificateDtoForUpdateWithoutId()));
    }

    @Test
    void throwExceptionByUpdateIfCertificateHasUniqueIdAndNotUniqueName() {
        assertThrows(EntityNotFoundException.class,
                () -> service.update(2, certificateDtoWithoutId()));
    }

    @Test
    void checkDeleteIfCertificateHasUniqueId() {
        service.delete(1);
        assertThrows(EntityNotFoundException.class, () -> service.findById(1));
    }

    @Test
    void throwExceptionByDeleteIfCertificateIdNotExist() {
        assertThrows(EntityNotFoundException.class, () -> service.delete(10));
    }
}
