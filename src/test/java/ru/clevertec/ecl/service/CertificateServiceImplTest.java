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
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.mapper.CertificateMapper;
import ru.clevertec.ecl.repository.CertificateRepository;
import ru.clevertec.ecl.service.impl.CertificateServiceImpl;

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
import static org.mockito.Mockito.verify;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoForUpdateDuration;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoForUpdateWithoutId;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoUpdatedDuration;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoWithId1;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoWithoutId;
import static ru.clevertec.ecl.util.CertificateUtil.certificateForUpdateWithId;
import static ru.clevertec.ecl.util.CertificateUtil.certificateUpdatedDuration;
import static ru.clevertec.ecl.util.CertificateUtil.certificateWithId1;
import static ru.clevertec.ecl.util.CertificateUtil.certificateWithoutId;
import static ru.clevertec.ecl.util.CertificateUtil.pageWithSizeOne;
import static ru.clevertec.ecl.util.CertificateUtil.pageable;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithId1;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithId20;
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
        List<CertificateDto> actual = service.findAllBy("fir", null, pageable());
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
        List<CertificateDto> actual = service.findAllBy(null, "fir", pageable());
        List<CertificateDto> expected = singletonList(certificateDtoWithId1());
        assertEquals(expected, actual);
        verify(certificateRepository).findAll(any(Example.class), any(PageRequest.class));
        verify(certificateMapper).toDto(certificateWithId1());
    }

    @Test
    void checkByTagName() {
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
        doReturn(Optional.of(certificateWithId1()))
                .when(certificateRepository)
                .findByNameIgnoreCase(certificateDtoWithoutId().getName());
        assertThrows(EntityNotFoundException.class,
                () -> service.save(certificateDtoWithoutId()));
    }

    @Test
    void checkUpdateIfCertificateHasUniqueIdAndUniqueName() {
        doReturn(Optional.of(certificateWithId1()))
                .when(certificateRepository).findById(1);
        doNothing()
                .when(certificateMapper)
                .updateDto(certificateDtoForUpdateDuration(), certificateDtoWithId1());
        doReturn(tagDtoWithId1())
                .when(tagService).saveOrUpdate(tagDtoWithoutId());
        doReturn(tagDtoWithId20())
                .when(tagService).saveOrUpdate(tagDtoWithoutId20());
        doReturn(certificateUpdatedDuration())
                .when(certificateMapper).toEntity(certificateDtoUpdatedDuration());
        doReturn(certificateUpdatedDuration())
                .when(certificateRepository).save(certificateUpdatedDuration());
        doReturn(certificateDtoUpdatedDuration())
                .when(certificateMapper).toDto(certificateUpdatedDuration());
        CertificateDto actual = service.update(1, certificateDtoForUpdateDuration());
        CertificateDto expected = certificateDtoUpdatedDuration();
        assertEquals(expected, actual);
        verify(certificateMapper).toEntity(certificateDtoUpdatedDuration());
        verify(certificateRepository).save(certificateUpdatedDuration());
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
