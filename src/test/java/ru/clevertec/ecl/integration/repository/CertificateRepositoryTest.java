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
import static ru.clevertec.ecl.util.CertificateUtil.certificateForSaveWithId;
import static ru.clevertec.ecl.util.CertificateUtil.certificateForSaveWithoutId;
import static ru.clevertec.ecl.util.CertificateUtil.certificateUpdated;
import static ru.clevertec.ecl.util.CertificateUtil.certificateUpdatedDuration;
import static ru.clevertec.ecl.util.CertificateUtil.certificateUpdatedPrice;
import static ru.clevertec.ecl.util.CertificateUtil.certificateWithId1;
import static ru.clevertec.ecl.util.CertificateUtil.certificateWithId3;
import static ru.clevertec.ecl.util.CertificateUtil.certificateWithId5;
import static ru.clevertec.ecl.util.CertificateUtil.certificates;
import static ru.clevertec.ecl.util.CertificateUtil.matcher;
import static ru.clevertec.ecl.util.CertificateUtil.pageable;

@RequiredArgsConstructor
public class CertificateRepositoryTest extends IntegrationTestBase {

    private final CertificateRepository repository;

    @Test
    void checkFindAll() {
        List<Certificate> actual = repository.findAll(pageable()).getContent();
        assertEquals(certificates(), actual);
    }

    @Test
    void checkFindAllByPartOfName() {
        Example<Certificate> example = Example.of(Certificate.builder()
                        .name("fir")
                        .build(),
                matcher());
        List<Certificate> actual = repository.findAll(example, pageable()).getContent();
        assertEquals(Collections.singletonList(certificateWithId1()), actual);
    }

    @Test
    void checkFindAllByPartOfDescription() {
        Example<Certificate> example = Example.of(Certificate.builder()
                        .description("desc")
                        .build(),
                matcher());
        List<Certificate> actual = repository.findAll(example, pageable()).getContent();
        assertEquals(certificates(), actual);
    }

    @Test
    void checkFindAllByTagName() {
        List<Certificate> actual = repository.findAllByTagName("new", pageable());
        assertEquals(certificates(), actual);
    }

    @Test
    void checkFindAllByTagName2() {
        List<Certificate> actual = repository.findAllByTagName("NeW", pageable());
        assertEquals(certificates(), actual);
    }

    @Test
    void checkFindAllBySeveralTagNames() {
        List<String> tagNames = Arrays.asList("cheap", "short");
        List<Certificate> actual = repository.findAllBySeveralTagNames(tagNames, pageable());
        List<Certificate> expected =
                Arrays.asList(certificateWithId1(), certificateWithId3(), certificateWithId5());
        assertEquals(expected, actual);
    }

    @Test
    void checkFindByIdIfCertificateIdExist() {
        Optional<Certificate> optional = repository.findById(1);
        optional.ifPresent(actual -> assertEquals(certificateWithId1(), actual));
    }

    @Test
    void checkFindByNameIfCertificateNameExist() {
        Optional<Certificate> optional = repository.findByNameIgnoreCase("first");
        optional.ifPresent(actual -> assertEquals(certificateWithId1(), actual));
    }

    @Test
    void checkFindByNameIfCertificateNameExist2() {
        Optional<Certificate> optional = repository.findByNameIgnoreCase("FiRsT");
        optional.ifPresent(actual -> assertEquals(certificateWithId1(), actual));
    }

    @Test
    void checkSaveIfCertificateHasUniqueName() {
        Certificate actual = repository.save(certificateForSaveWithoutId());
        assertEquals(certificateForSaveWithId(), actual);
    }

    @Test
    void checkUpdateIfCertificateHasUniqueIdAndUniqueName() {
        Certificate actual = repository.save(certificateUpdated());
        assertEquals(certificateUpdated(), actual);
    }

    @Test
    void checkUpdatePriceIfCertificateHasUniqueId() {
        Certificate actual = repository.save(certificateUpdatedPrice());
        assertEquals(certificateUpdatedPrice(), actual);
    }

    @Test
    void checkUpdateDurationIfCertificateHasUniqueId() {
        Certificate actual = repository.save(certificateUpdatedDuration());
        assertEquals(certificateUpdatedDuration(), actual);
    }

    @Test
    void checkDeleteIfCertificateHasUniqueId() {
        repository.deleteById(1);
        Optional<Certificate> optional = repository.findById(1);
        assertFalse(optional.isPresent());
    }
}
