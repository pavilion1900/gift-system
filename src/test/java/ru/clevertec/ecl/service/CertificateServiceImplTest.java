package ru.clevertec.ecl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.TagDto;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateForUpdateWithId;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificatePriceDto;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateUpdated;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateUpdatedDuration;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateUpdatedPrice;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateWithId1;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateWithId3;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateWithId5;
import static ru.clevertec.ecl.testdata.CertificateUtil.certificateWithoutId;
import static ru.clevertec.ecl.testdata.CertificateUtil.pageWithSizeOne;
import static ru.clevertec.ecl.testdata.CertificateUtil.pageable;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoWithId1;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoWithId5;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoWithoutId;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private TagService tagService;

    @Mock
    private CertificateMapper certificateMapper;

    @InjectMocks
    private CertificateServiceImpl certificateService;

    @Test
    void checkFindAll() {
        doReturn(new PageImpl<>(singletonList(certificateWithId1())))
                .when(certificateRepository).findAll(pageWithSizeOne());
        doReturn(certificateDtoWithId1())
                .when(certificateMapper).toDto(certificateWithId1());
        List<CertificateDto> actual = certificateService.findAll(pageWithSizeOne());
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
        List<CertificateDto> actual = certificateService.findAllByIgnoreCase("fir", null, pageable());
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
        List<CertificateDto> actual = certificateService.findAllByIgnoreCase(null, "fir", pageable());
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
        List<CertificateDto> actual = certificateService.findAllByTagName("new", pageWithSizeOne());
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
        List<CertificateDto> actual = certificateService.findAllBySeveralTagNames(tagNames, pageable());
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
        CertificateDto actual = certificateService.findById(1);
        CertificateDto expected = certificateDtoWithId1();
        assertEquals(expected, actual);
        verify(certificateRepository).findById(1);
        verify(certificateMapper).toDto(certificateWithId1());
    }

    @Test
    void throwExceptionIfCertificateIdNotExist() {
        doReturn(Optional.empty())
                .when(certificateRepository).findById(1);
        assertThrows(EntityNotFoundException.class, () -> certificateService.findById(1));
    }

    @Test
    void checkFindByNameIfCertificateNameExist() {
        doReturn(Optional.of(certificateWithId1()))
                .when(certificateRepository).findByNameIgnoreCase("first");
        doReturn(certificateDtoWithId1())
                .when(certificateMapper).toDto(certificateWithId1());
        CertificateDto actual = certificateService.findByNameIgnoreCase("first");
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
        CertificateDto actual = certificateService.findByNameIgnoreCase("FiRsT");
        CertificateDto expected = certificateDtoWithId1();
        assertEquals(expected, actual);
        verify(certificateRepository).findByNameIgnoreCase("FiRsT");
        verify(certificateMapper).toDto(certificateWithId1());
    }

    @Test
    void throwExceptionIfCertificateNameNotExist() {
        doReturn(Optional.empty())
                .when(certificateRepository).findByNameIgnoreCase("first");
        assertThrows(EntityNotFoundException.class, () -> certificateService.findByNameIgnoreCase("first"));
    }

    @Test
    void checkSaveIfCertificateHasUniqueName() {
        doReturn(tagDtoWithoutId())
                .when(tagService).saveOrUpdate(any(TagDto.class));
        doReturn(certificateWithoutId())
                .when(certificateMapper).toEntity(certificateDtoWithoutId());
        doReturn(certificateWithId1())
                .when(certificateRepository).save(certificateWithoutId());
        doReturn(certificateDtoWithId1())
                .when(certificateMapper).toDto(certificateWithId1());
        CertificateDto actual = certificateService.save(certificateDtoWithoutId());
        CertificateDto expected = certificateDtoWithId1();
        assertEquals(expected, actual);
    }

    @Test
    void throwExceptionBySaveIfCertificateHasNotUniqueName() {
        doReturn(tagDtoWithoutId())
                .when(tagService).saveOrUpdate(any(TagDto.class));
        doReturn(certificateWithoutId())
                .when(certificateMapper).toEntity(certificateDtoWithoutId());
        doThrow(DataIntegrityViolationException.class)
                .when(certificateRepository).save(certificateWithoutId());
        assertThrows(DataIntegrityViolationException.class, () -> certificateService.save(certificateDtoWithoutId()));
    }

    @Test
    void checkUpdateIfCertificateHasUniqueIdAndUniqueName() {
        doReturn(Optional.of(certificateWithId1()))
                .when(certificateRepository).findById(1);
        doNothing()
                .when(certificateMapper).updateDto(certificateDtoForUpdate(), certificateDtoWithId1());
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
        CertificateDto actual = certificateService.update(1, certificateDtoForUpdate());
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
                () -> certificateService.update(1, certificateDtoForUpdateWithoutId()));
        verify(certificateRepository, never()).save(certificateForUpdateWithId());
    }

    @Test
    void checkUpdatePriceIfCertificateHasUniqueId() {
        doReturn(Optional.of(certificateWithId1()))
                .when(certificateRepository).findById(1);
        doNothing()
                .when(certificateMapper).updatePriceDto(certificatePriceDto(), certificateDtoWithId1());
        doReturn(certificateUpdatedPrice())
                .when(certificateMapper).toEntity(certificateDtoUpdatedPrice());
        doReturn(certificateUpdatedPrice())
                .when(certificateRepository).save(certificateUpdatedPrice());
        doReturn(certificateDtoUpdatedPrice())
                .when(certificateMapper).toDto(certificateUpdatedPrice());
        CertificateDto actual = certificateService.updatePrice(1, certificatePriceDto());
        CertificateDto expected = certificateDtoUpdatedPrice();
        assertEquals(expected, actual);
        verify(certificateMapper).toEntity(certificateDtoUpdatedPrice());
        verify(certificateRepository).save(certificateUpdatedPrice());
    }

    @Test
    void throwExceptionByUpdatePriceIfCertificateIdNotExist() {
        doReturn(Optional.empty())
                .when(certificateRepository).findById(1);
        assertThrows(EntityNotFoundException.class, () -> certificateService.updatePrice(1, certificatePriceDto()));
        verify(certificateRepository, never()).save(certificateUpdatedPrice());
    }

    @Test
    void checkUpdateDurationIfCertificateHasUniqueId() {
        doReturn(Optional.of(certificateWithId1()))
                .when(certificateRepository).findById(1);
        doNothing()
                .when(certificateMapper).updateDurationDto(certificateDurationDto(), certificateDtoWithId1());
        doReturn(certificateUpdatedDuration())
                .when(certificateMapper).toEntity(certificateDtoUpdatedDuration());
        doReturn(certificateUpdatedDuration())
                .when(certificateRepository).save(certificateUpdatedDuration());
        doReturn(certificateDtoUpdatedDuration())
                .when(certificateMapper).toDto(certificateUpdatedDuration());
        CertificateDto actual = certificateService.updateDuration(1, certificateDurationDto());
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
                () -> certificateService.updateDuration(1, certificateDurationDto()));
        verify(certificateRepository, never()).save(certificateUpdatedDuration());
    }

    @Test
    void checkDeleteIfCertificateHasUniqueId() {
        doReturn(Optional.of(certificateWithId1()))
                .when(certificateRepository).findById(1);
        assertDoesNotThrow(() -> certificateService.delete(1));
        verify(certificateRepository).deleteById(1);
    }

    @Test
    void throwExceptionByDeleteIfCertificateIdNotExist() {
        doReturn(Optional.empty())
                .when(certificateRepository).findById(1);
        assertThrows(EntityNotFoundException.class, () -> certificateService.delete(1));
        verify(certificateRepository, never()).deleteById(1);
    }
}
