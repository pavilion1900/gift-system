package ru.clevertec.ecl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.entity.Certificate;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.mapper.CertificateMapper;
import ru.clevertec.ecl.repository.CertificateRepository;
import ru.clevertec.ecl.service.impl.CertificateServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoForUpdate;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoForUpdateWithoutId;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoUpdated;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoUpdatedDuration;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoUpdatedPrice;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoWithId1;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoWithId3;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoWithId5;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoWithoutId;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDurationDto;
import static ru.clevertec.ecl.util.CertificateUtil.certificateForUpdateWithId;
import static ru.clevertec.ecl.util.CertificateUtil.certificatePriceDto;
import static ru.clevertec.ecl.util.CertificateUtil.certificateUpdated;
import static ru.clevertec.ecl.util.CertificateUtil.certificateUpdatedDuration;
import static ru.clevertec.ecl.util.CertificateUtil.certificateUpdatedPrice;
import static ru.clevertec.ecl.util.CertificateUtil.certificateWithId1;
import static ru.clevertec.ecl.util.CertificateUtil.certificateWithId3;
import static ru.clevertec.ecl.util.CertificateUtil.certificateWithId5;
import static ru.clevertec.ecl.util.CertificateUtil.certificateWithoutId;
import static ru.clevertec.ecl.util.CertificateUtil.pageWithSizeOne;
import static ru.clevertec.ecl.util.CertificateUtil.pageable;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithId1;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithId20;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithId5;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithoutId;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithoutId20;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private TagService tagService;

    @Mock
    private CertificateMapper certificateMapper;

    @InjectMocks
    private CertificateServiceImpl service;

    @Test
    void checkFindAll() {
        doReturn(new PageImpl<>(singletonList(certificateWithId1())))
                .when(certificateRepository).findAll(pageWithSizeOne());
        doReturn(certificateDtoWithId1())
                .when(certificateMapper).toDto(certificateWithId1());
        List<CertificateDto> actual = service.findAll(pageWithSizeOne());
        List<CertificateDto> expected = singletonList(certificateDtoWithId1());
        assertEquals(expected, actual);
        verify(certificateRepository).findAll(pageWithSizeOne());
        verify(certificateMapper).toDto(certificateWithId1());
    }

    @Test
    void checkFindAllByPartOfName() {
        doReturn(new PageImpl<>(singletonList(certificateWithId1())))
                .when(certificateRepository).findAll(any(Example.class), any(PageRequest.class));
        doReturn(certificateDtoWithId1())
                .when(certificateMapper).toDto(certificateWithId1());
        List<CertificateDto> actual = service.findAllByIgnoreCase("fir", null, pageable());
        List<CertificateDto> expected = singletonList(certificateDtoWithId1());
        assertEquals(expected, actual);
        verify(certificateRepository).findAll(any(Example.class), any(PageRequest.class));
        verify(certificateMapper).toDto(certificateWithId1());
    }

    @Test
    void checkFindAllByPartOfDescription() {
        doReturn(new PageImpl<>(singletonList(certificateWithId1())))
                .when(certificateRepository).findAll(any(Example.class), any(PageRequest.class));
        doReturn(certificateDtoWithId1())
                .when(certificateMapper).toDto(certificateWithId1());
        List<CertificateDto> actual = service.findAllByIgnoreCase(null, "fir", pageable());
        List<CertificateDto> expected = singletonList(certificateDtoWithId1());
        assertEquals(expected, actual);
        verify(certificateRepository).findAll(any(Example.class), any(PageRequest.class));
        verify(certificateMapper).toDto(certificateWithId1());
    }

    @Test
    void checkFindAllByTagName() {
        doReturn(singletonList(certificateWithId1()))
                .when(certificateRepository).findAllByTagName("new", pageWithSizeOne());
        doReturn(certificateDtoWithId1())
                .when(certificateMapper).toDto(certificateWithId1());
        List<CertificateDto> actual = service.findAllByTagName("new", pageWithSizeOne());
        List<CertificateDto> expected = singletonList(certificateDtoWithId1());
        assertEquals(expected, actual);
        verify(certificateRepository).findAllByTagName("new", pageWithSizeOne());
        verify(certificateMapper).toDto(certificateWithId1());
    }

    @Test
    void checkFindAllBySeveralTagNames() {
        List<String> tagNames = Arrays.asList("cheap", "short");
        List<Certificate> certificates =
                Arrays.asList(certificateWithId1(), certificateWithId3(), certificateWithId5());
        doReturn(certificates)
                .when(certificateRepository).findAllBySeveralTagNames(tagNames, pageable());
        doReturn(certificateDtoWithId1())
                .when(certificateMapper).toDto(certificateWithId1());
        doReturn(certificateDtoWithId3())
                .when(certificateMapper).toDto(certificateWithId3());
        doReturn(certificateDtoWithId5())
                .when(certificateMapper).toDto(certificateWithId5());
        List<CertificateDto> actual = service.findAllBySeveralTagNames(tagNames, pageable());
        List<CertificateDto> expected = Arrays.asList(
                certificateDtoWithId1(), certificateDtoWithId3(), certificateDtoWithId5());
        assertEquals(expected, actual);
        verify(certificateRepository).findAllBySeveralTagNames(tagNames, pageable());
        verify(certificateMapper, times(3)).toDto(any(Certificate.class));
    }

    @Test
    void checkFindByIdIfCertificateIdExist() {
        doReturn(Optional.of(certificateWithId1()))
                .when(certificateRepository).findById(1);
        doReturn(certificateDtoWithId1())
                .when(certificateMapper).toDto(certificateWithId1());
        CertificateDto actual = service.findById(1);
        CertificateDto expected = certificateDtoWithId1();
        assertEquals(expected, actual);
        verify(certificateRepository).findById(1);
        verify(certificateMapper).toDto(certificateWithId1());
    }

    @Test
    void throwExceptionIfCertificateIdNotExist() {
        doReturn(Optional.empty())
                .when(certificateRepository).findById(1);
        assertThrows(EntityNotFoundException.class, () -> service.findById(1));
    }

    @Test
    void checkFindByNameIfCertificateNameExist() {
        doReturn(Optional.of(certificateWithId1()))
                .when(certificateRepository).findByNameIgnoreCase("first");
        doReturn(certificateDtoWithId1())
                .when(certificateMapper).toDto(certificateWithId1());
        CertificateDto actual = service.findByName("first");
        CertificateDto expected = certificateDtoWithId1();
        assertEquals(expected, actual);
        verify(certificateRepository).findByNameIgnoreCase("first");
        verify(certificateMapper).toDto(certificateWithId1());
    }

    @Test
    void checkFindByNameIfCertificateNameExist2() {
        doReturn(Optional.of(certificateWithId1()))
                .when(certificateRepository).findByNameIgnoreCase("FiRsT");
        doReturn(certificateDtoWithId1())
                .when(certificateMapper).toDto(certificateWithId1());
        CertificateDto actual = service.findByName("FiRsT");
        CertificateDto expected = certificateDtoWithId1();
        assertEquals(expected, actual);
        verify(certificateRepository).findByNameIgnoreCase("FiRsT");
        verify(certificateMapper).toDto(certificateWithId1());
    }

    @Test
    void throwExceptionIfCertificateNameNotExist() {
        doReturn(Optional.empty())
                .when(certificateRepository).findByNameIgnoreCase("first");
        assertThrows(EntityNotFoundException.class, () -> service.findByName("first"));
    }

    @Test
    void checkSaveIfCertificateHasUniqueName() {
        doReturn(tagDtoWithId1())
                .when(tagService).saveOrUpdate(tagDtoWithoutId());
        doReturn(tagDtoWithId20())
                .when(tagService).saveOrUpdate(tagDtoWithoutId20());
        doReturn(certificateWithoutId())
                .when(certificateMapper).toEntity(certificateDtoWithoutId());
        doReturn(certificateWithId1())
                .when(certificateRepository).save(certificateWithoutId());
        doReturn(certificateDtoWithId1())
                .when(certificateMapper).toDto(certificateWithId1());
        CertificateDto actual = service.save(certificateDtoWithoutId());
        CertificateDto expected = certificateDtoWithId1();
        assertEquals(expected, actual);
        verify(certificateMapper).toEntity(certificateDtoWithoutId());
        verify(certificateRepository).save(certificateWithoutId());
        verify(certificateMapper).toDto(certificateWithId1());
    }

    @Test
    void throwExceptionBySaveIfCertificateHasNotUniqueName() {
        doReturn(true)
                .when(certificateRepository)
                .existsByNameIgnoreCase(certificateDtoWithoutId().getName());
        assertThrows(EntityNotFoundException.class,
                () -> service.save(certificateDtoWithoutId()));
    }

    @Test
    void checkUpdateIfCertificateHasUniqueIdAndUniqueName() {
        doReturn(Optional.of(certificateWithId1()))
                .when(certificateRepository).findById(1);
        doNothing()
                .when(certificateMapper)
                .updateDto(certificateDtoForUpdate(), certificateDtoWithId1());
        doReturn(tagDtoWithId1())
                .when(tagService).saveOrUpdate(tagDtoWithId1());
        doReturn(tagDtoWithId5())
                .when(tagService).saveOrUpdate(tagDtoWithId5());
        doReturn(certificateUpdated())
                .when(certificateMapper).toEntity(certificateDtoUpdated());
        doReturn(certificateUpdated())
                .when(certificateRepository).save(certificateUpdated());
        doReturn(certificateDtoUpdated())
                .when(certificateMapper).toDto(certificateUpdated());
        CertificateDto actual = service.update(1, certificateDtoForUpdate());
        CertificateDto expected = certificateDtoUpdated();
        assertEquals(expected, actual);
        verify(certificateMapper).toEntity(certificateDtoUpdated());
        verify(certificateRepository).save(certificateUpdated());
    }

    @Test
    void throwExceptionByUpdateIfCertificateIdNotExist() {
        doReturn(Optional.empty())
                .when(certificateRepository).findById(1);
        assertThrows(EntityNotFoundException.class,
                () -> service.update(1, certificateDtoForUpdateWithoutId()));
        verify(certificateRepository, never()).save(certificateForUpdateWithId());
    }

    @Test
    void checkUpdatePriceIfCertificateHasUniqueId() {
        doReturn(Optional.of(certificateWithId1()))
                .when(certificateRepository).findById(1);
        doNothing()
                .when(certificateMapper)
                .updatePriceDto(certificatePriceDto(), certificateDtoWithId1());
        doReturn(certificateUpdatedPrice())
                .when(certificateMapper).toEntity(certificateDtoUpdatedPrice());
        doReturn(certificateUpdatedPrice())
                .when(certificateRepository).save(certificateUpdatedPrice());
        doReturn(certificateDtoUpdatedPrice())
                .when(certificateMapper).toDto(certificateUpdatedPrice());
        CertificateDto actual = service.updatePrice(1, certificatePriceDto());
        CertificateDto expected = certificateDtoUpdatedPrice();
        assertEquals(expected, actual);
        verify(certificateMapper).toEntity(certificateDtoUpdatedPrice());
        verify(certificateRepository).save(certificateUpdatedPrice());
    }

    @Test
    void throwExceptionByUpdatePriceIfCertificateIdNotExist() {
        doReturn(Optional.empty())
                .when(certificateRepository).findById(1);
        assertThrows(EntityNotFoundException.class,
                () -> service.updatePrice(1, certificatePriceDto()));
        verify(certificateRepository, never()).save(certificateUpdatedPrice());
    }

    @Test
    void checkUpdateDurationIfCertificateHasUniqueId() {
        doReturn(Optional.of(certificateWithId1()))
                .when(certificateRepository).findById(1);
        doNothing()
                .when(certificateMapper)
                .updateDurationDto(certificateDurationDto(), certificateDtoWithId1());
        doReturn(certificateUpdatedDuration())
                .when(certificateMapper).toEntity(certificateDtoUpdatedDuration());
        doReturn(certificateUpdatedDuration())
                .when(certificateRepository).save(certificateUpdatedDuration());
        doReturn(certificateDtoUpdatedDuration())
                .when(certificateMapper).toDto(certificateUpdatedDuration());
        CertificateDto actual = service.updateDuration(1, certificateDurationDto());
        CertificateDto expected = certificateDtoUpdatedDuration();
        assertEquals(expected, actual);
        verify(certificateMapper).toEntity(certificateDtoUpdatedDuration());
        verify(certificateRepository).save(certificateUpdatedDuration());
    }

    @Test
    void throwExceptionByUpdateDurationIfCertificateIdNotExist() {
        doReturn(Optional.empty())
                .when(certificateRepository).findById(1);
        assertThrows(EntityNotFoundException.class,
                () -> service.updateDuration(1, certificateDurationDto()));
        verify(certificateRepository, never()).save(certificateUpdatedDuration());
    }

    @Test
    void checkDeleteIfCertificateHasUniqueId() {
        doReturn(Optional.of(certificateWithId1()))
                .when(certificateRepository).findById(1);
        assertDoesNotThrow(() -> service.delete(1));
        verify(certificateRepository).deleteById(1);
    }

    @Test
    void throwExceptionByDeleteIfCertificateIdNotExist() {
        doReturn(Optional.empty())
                .when(certificateRepository).findById(1);
        assertThrows(EntityNotFoundException.class, () -> service.delete(1));
        verify(certificateRepository, never()).deleteById(1);
    }
}
