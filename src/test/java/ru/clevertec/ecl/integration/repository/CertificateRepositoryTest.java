package ru.clevertec.ecl.integration.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import ru.clevertec.ecl.entity.Certificate;
import ru.clevertec.ecl.integration.IntegrationTestBase;
import ru.clevertec.ecl.repository.CertificateRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateForSaveWithId;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateForSaveWithoutId;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateUpdated;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateUpdatedDuration;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateUpdatedPrice;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateWithId1;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateWithId3;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateWithId5;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificates;
import static ru.clevertec.ecl.testdata.CertificateUtil.matcher;
import static ru.clevertec.ecl.testdata.CertificateUtil.pageable;

@RequiredArgsConstructor
public class CertificateRepositoryTest extends IntegrationTestBase {

    private final CertificateRepository certificateRepository;

    @Test
    void checkFindAll() {
        List<Certificate> actual = certificateRepository.findAll(pageable()).getContent();
        assertEquals(certificates(), actual);
    }

    @Test
    void checkFindAllByPartOfName() {
        Example<Certificate> example = Example.of(Certificate.builder()
                        .name("fir")
                        .build(),
                matcher());
        List<Certificate> actual = certificateRepository.findAll(example, pageable()).getContent();
        assertEquals(Collections.singletonList(certificateWithId1()), actual);
    }

    @Test
    void checkFindAllByPartOfDescription() {
        Example<Certificate> example = Example.of(Certificate.builder()
                        .description("desc")
                        .build(),
                matcher());
        List<Certificate> actual = certificateRepository.findAll(example, pageable()).getContent();
        assertEquals(certificates(), actual);
    }

    @Test
    void checkFindAllByTagName() {
        List<Certificate> actual = certificateRepository.findAllByTagName("new", pageable());
        assertEquals(certificates(), actual);
    }

    @Test
    void checkFindAllByTagName2() {
        List<Certificate> actual = certificateRepository.findAllByTagName("NeW", pageable());
        assertEquals(certificates(), actual);
    }

    @Test
    void checkFindAllBySeveralTagNames() {
        List<String> tagNames = Arrays.asList("cheap", "short");
        List<Certificate> actual = certificateRepository.findAllBySeveralTagNames(tagNames, pageable());
        List<Certificate> expected = Arrays.asList(certificateWithId1(), certificateWithId3(), certificateWithId5());
        assertEquals(expected, actual);
    }

    @Test
    void checkFindByIdIfCertificateIdExist() {
        Optional<Certificate> optional = certificateRepository.findById(1);
        optional.ifPresent(actual -> assertEquals(certificateWithId1(), actual));
    }

    @Test
    void checkFindByNameIfCertificateNameExist() {
        Optional<Certificate> optional = certificateRepository.findByNameIgnoreCase("first");
        optional.ifPresent(actual -> assertEquals(certificateWithId1(), actual));
    }

    @Test
    void checkFindByNameIfCertificateNameExist2() {
        Optional<Certificate> optional = certificateRepository.findByNameIgnoreCase("FiRsT");
        optional.ifPresent(actual -> assertEquals(certificateWithId1(), actual));
    }

    @Test
    void checkSaveIfCertificateHasUniqueName() {
        Certificate actual = certificateRepository.save(certificateForSaveWithoutId());
        certificateRepository.flush();
        assertEquals(certificateForSaveWithId(), actual);
    }

    @Test
    void checkUpdateIfCertificateHasUniqueIdAndUniqueName() {
        Certificate actual = certificateRepository.save(certificateUpdated());
        certificateRepository.flush();
        assertEquals(certificateUpdated(), actual);
    }

    @Test
    void checkUpdatePriceIfCertificateHasUniqueId() {
        Certificate actual = certificateRepository.save(certificateUpdatedPrice());
        certificateRepository.flush();
        assertEquals(certificateUpdatedPrice(), actual);
    }

    @Test
    void checkUpdateDurationIfCertificateHasUniqueId() {
        Certificate actual = certificateRepository.save(certificateUpdatedDuration());
        certificateRepository.flush();
        assertEquals(certificateUpdatedDuration(), actual);
    }

    @Test
    void checkDeleteIfCertificateHasUniqueId() {
        certificateRepository.deleteById(1);
        Optional<Certificate> optional = certificateRepository.findById(1);
        certificateRepository.flush();
        assertFalse(optional.isPresent());
    }
}
