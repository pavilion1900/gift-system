package ru.clevertec.ecl.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.integration.IntegrationTestBase;
import ru.clevertec.ecl.repository.CertificateRepository;
import ru.clevertec.ecl.service.CertificateService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateDtoForSaveWithId;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateDtoForSaveWithoutId;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateDtoForUpdate;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateDtoForUpdateWithoutId;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateDtoUpdated;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateDtoUpdatedDuration;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateDtoUpdatedPrice;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateDtoWithId1;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateDtoWithId3;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateDtoWithId5;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateDtoWithoutId;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateDurationDto;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificatePriceDto;
import static ru.clevertec.ecl.testdata.CertificateUtil.dtoCertificates;
import static ru.clevertec.ecl.testdata.CertificateUtil.pageable;

@RequiredArgsConstructor
public class CertificateServiceImplTest extends IntegrationTestBase {

    private final CertificateService certificateService;
    private final CertificateRepository certificateRepository;

    @Test
    void checkFindAll() {
        List<CertificateDto> actual = certificateService.findAll(pageable());
        assertEquals(dtoCertificates(), actual);
    }

    @Test
    void checkFindAllByPartOfName() {
        List<CertificateDto> actual = certificateService.findAllByIgnoreCase("fir", null, pageable());
        assertEquals(Collections.singletonList(certificateDtoWithId1()), actual);
    }

    @Test
    void checkFindAllByPartOfDescription() {
        List<CertificateDto> actual = certificateService.findAllByIgnoreCase(null, "desc", pageable());
        assertEquals(dtoCertificates(), actual);
    }

    @Test
    void checkFindAllByTagName() {
        List<CertificateDto> actual = certificateService.findAllByTagName("new", pageable());
        assertEquals(dtoCertificates(), actual);
    }

    @Test
    void checkFindAllBySeveralTagNames() {
        List<String> tagNames = Arrays.asList("cheap", "short");
        List<CertificateDto> actual = certificateService.findAllBySeveralTagNames(tagNames, pageable());
        List<CertificateDto> expected = Arrays.asList(
                certificateDtoWithId1(), certificateDtoWithId3(), certificateDtoWithId5());
        assertEquals(expected, actual);
    }

    @Test
    void checkFindByIdIfCertificateIdExist() {
        CertificateDto actual = certificateService.findById(1);
        assertEquals(certificateDtoWithId1(), actual);
    }

    @Test
    void throwExceptionIfCertificateIdNotExist() {
        assertThrows(EntityNotFoundException.class, () -> certificateService.findById(10));
    }

    @Test
    void checkFindByNameIfCertificateNameExist() {
        CertificateDto actual = certificateService.findByNameIgnoreCase("first");
        assertEquals(certificateDtoWithId1(), actual);
    }

    @Test
    void checkFindByNameIfCertificateNameExist2() {
        CertificateDto actual = certificateService.findByNameIgnoreCase("FiRsT");
        assertEquals(certificateDtoWithId1(), actual);
    }

    @Test
    void throwExceptionIfCertificateNameNotExist() {
        assertThrows(EntityNotFoundException.class, () -> certificateService.findByNameIgnoreCase("short333"));
    }

    @Test
    void checkSaveIfCertificateHasUniqueName() {
        CertificateDto actual = certificateService.save(certificateDtoForSaveWithoutId());
        certificateRepository.flush();
        assertEquals(certificateDtoForSaveWithId(), actual);
    }

    @Test
    void checkUpdateIfCertificateHasUniqueIdAndUniqueName() {
        CertificateDto actual = certificateService.update(1, certificateDtoForUpdate());
        certificateRepository.flush();
        assertEquals(certificateDtoUpdated(), actual);
    }

    @Test
    void throwExceptionByUpdateIfCertificateIdNotExist() {
        assertThrows(EntityNotFoundException.class,
                () -> certificateService.update(10, certificateDtoForUpdateWithoutId()));
    }

    @Test
    void throwExceptionByUpdateIfCertificateHasUniqueIdAndNotUniqueName() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            certificateService.update(2, certificateDtoWithoutId());
            certificateRepository.flush();
        });
    }

    @Test
    void checkUpdatePriceIfCertificateHasUniqueId() {
        CertificateDto actual = certificateService.updatePrice(1, certificatePriceDto());
        certificateRepository.flush();
        assertEquals(certificateDtoUpdatedPrice(), actual);
    }

    @Test
    void throwExceptionByUpdatePriceIfCertificateIdNotExist() {
        assertThrows(EntityNotFoundException.class, () -> certificateService.updatePrice(10, certificatePriceDto()));
    }

    @Test
    void checkUpdateDurationIfCertificateHasUniqueId() {
        CertificateDto actual = certificateService.updateDuration(1, certificateDurationDto());
        certificateRepository.flush();
        assertEquals(certificateDtoUpdatedDuration(), actual);
    }

    @Test
    void throwExceptionByUpdateDurationIfCertificateIdNotExist() {
        assertThrows(EntityNotFoundException.class,
                () -> certificateService.updateDuration(10, certificateDurationDto()));
    }

    @Test
    void checkDeleteIfCertificateHasUniqueId() {
        certificateService.delete(1);
        certificateRepository.flush();
        assertThrows(EntityNotFoundException.class, () -> certificateService.findById(1));
    }

    @Test
    void throwExceptionByDeleteIfCertificateIdNotExist() {
        assertThrows(EntityNotFoundException.class, () -> certificateService.delete(10));
    }
}
