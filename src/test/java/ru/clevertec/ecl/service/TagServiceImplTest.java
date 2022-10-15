package ru.clevertec.ecl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.service.impl.TagServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.ecl.testdata.TagUtil.dtoTags;
import static ru.clevertec.ecl.testdata.TagUtil.pageable;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoForUpdateWithId;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoForUpdateWithoutId;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoWithId1;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoWithId2;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoWithId3;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoWithId4;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoWithId5;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoWithId6;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoWithoutId;
import static ru.clevertec.ecl.testdata.TagUtil.tagForUpdateWithId;
import static ru.clevertec.ecl.testdata.TagUtil.tagForUpdateWithId2;
import static ru.clevertec.ecl.testdata.TagUtil.tagWithId1;
import static ru.clevertec.ecl.testdata.TagUtil.tagWithId2;
import static ru.clevertec.ecl.testdata.TagUtil.tagWithId3;
import static ru.clevertec.ecl.testdata.TagUtil.tagWithId4;
import static ru.clevertec.ecl.testdata.TagUtil.tagWithId5;
import static ru.clevertec.ecl.testdata.TagUtil.tagWithId6;
import static ru.clevertec.ecl.testdata.TagUtil.tagWithoutId;
import static ru.clevertec.ecl.testdata.TagUtil.tags;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void checkFindAll() {
        doReturn(new PageImpl<>(tags()))
                .when(tagRepository).findAll(pageable());
        doReturn(tagDtoWithId1())
                .when(tagMapper).toDto(tagWithId1());
        doReturn(tagDtoWithId2())
                .when(tagMapper).toDto(tagWithId2());
        doReturn(tagDtoWithId3())
                .when(tagMapper).toDto(tagWithId3());
        doReturn(tagDtoWithId4())
                .when(tagMapper).toDto(tagWithId4());
        doReturn(tagDtoWithId5())
                .when(tagMapper).toDto(tagWithId5());
        doReturn(tagDtoWithId6())
                .when(tagMapper).toDto(tagWithId6());
        List<TagDto> actual = tagService.findAll(pageable());
        List<TagDto> expected = new ArrayList<>(dtoTags());
        assertEquals(expected, actual);
        verify(tagRepository).findAll(pageable());
        verify(tagMapper, times(6)).toDto(any(Tag.class));
    }

    @Test
    void checkFindByIdIfTagIdExist() {
        doReturn(Optional.of(tagWithId1()))
                .when(tagRepository).findById(1);
        doReturn(tagDtoWithId1())
                .when(tagMapper).toDto(tagWithId1());
        TagDto actual = tagService.findById(1);
        TagDto expected = tagDtoWithId1();
        assertEquals(expected, actual);
        verify(tagRepository).findById(1);
        verify(tagMapper).toDto(tagWithId1());
    }

    @Test
    void throwExceptionIfTagIdNotExist() {
        doReturn(Optional.empty())
                .when(tagRepository).findById(1);
        assertThrows(EntityNotFoundException.class, () -> tagService.findById(1));
    }

    @Test
    void checkFindByNameIfTagNameExist() {
        doReturn(Optional.of(tagWithId1()))
                .when(tagRepository).findByNameIgnoreCase("short");
        doReturn(tagDtoWithId1())
                .when(tagMapper).toDto(tagWithId1());
        TagDto actual = tagService.findByNameIgnoreCase("short");
        TagDto expected = tagDtoWithId1();
        assertEquals(expected, actual);
        verify(tagRepository).findByNameIgnoreCase("short");
        verify(tagMapper).toDto(tagWithId1());
    }

    @Test
    void throwExceptionIfTagNameNotExist() {
        doReturn(Optional.empty())
                .when(tagRepository).findByNameIgnoreCase("short");
        assertThrows(EntityNotFoundException.class, () -> tagService.findByNameIgnoreCase("short"));
    }

    @Test
    void checkFindMostWidelyUsedTag() {
        doReturn(Optional.of(tagWithId1()))
                .when(tagRepository).findMostWidelyUsedTag();
        doReturn(tagDtoWithId1())
                .when(tagMapper).toDto(tagWithId1());
        TagDto actual = tagService.findMostWidelyUsedTag();
        TagDto expected = tagDtoWithId1();
        assertEquals(expected, actual);
        verify(tagRepository).findMostWidelyUsedTag();
        verify(tagMapper).toDto(tagWithId1());
    }

    @Test
    void checkSaveIfTagHasUniqueName() {
        doReturn(tagWithoutId())
                .when(tagMapper).toEntity(tagDtoWithoutId());
        doReturn(tagWithId1())
                .when(tagRepository).save(tagWithoutId());
        doReturn(tagDtoWithId1())
                .when(tagMapper).toDto(tagWithId1());
        TagDto actual = tagService.save(tagDtoWithoutId());
        TagDto expected = tagDtoWithId1();
        assertEquals(expected, actual);
        verify(tagMapper).toEntity(tagDtoWithoutId());
        verify(tagRepository).save(tagWithoutId());
        verify(tagMapper).toDto(tagWithId1());
    }

    @Test
    void throwExceptionBySaveIfTagHasNotUniqueName() {
        doReturn(tagWithoutId())
                .when(tagMapper).toEntity(tagDtoWithoutId());
        doThrow(DataIntegrityViolationException.class)
                .when(tagRepository).save(tagWithoutId());
        assertThrows(DataIntegrityViolationException.class, () -> tagService.save(tagDtoWithoutId()));
    }

    @Test
    void checkUpdateIfTagHasUniqueIdAndUniqueName() {
        doReturn(Optional.of(tagWithId1()))
                .when(tagRepository).findById(1);
        doReturn(tagForUpdateWithId())
                .when(tagRepository).save(tagForUpdateWithId());
        doReturn(tagDtoForUpdateWithId())
                .when(tagMapper).toDto(tagForUpdateWithId());
        TagDto actual = tagService.update(1, tagDtoForUpdateWithoutId());
        TagDto expected = tagDtoForUpdateWithId();
        assertEquals(expected, actual);
        verify(tagRepository).findById(1);
        verify(tagRepository).save(tagForUpdateWithId());
        verify(tagMapper).toDto(tagForUpdateWithId());
    }

    @Test
    void throwExceptionByUpdateIfTagIdNotExist() {
        doReturn(Optional.empty())
                .when(tagRepository).findById(1);
        assertThrows(EntityNotFoundException.class, () -> tagService.update(1, tagDtoWithoutId()));
        verify(tagRepository, never()).save(tagWithId1());
    }

    @Test
    void throwExceptionByUpdateIfTagHasUniqueIdAndNotUniqueName() {
        doReturn(Optional.of(tagWithId2()))
                .when(tagRepository).findById(2);
        when(tagRepository.save(tagForUpdateWithId2()))
                .thenThrow(DataIntegrityViolationException.class);
        assertThrows(DataIntegrityViolationException.class, () -> tagService.update(2, tagDtoWithoutId()));
    }

    @Test
    void checkDeleteIfTagHasUniqueId() {
        doReturn(Optional.of(tagWithId1()))
                .when(tagRepository).findById(1);
        assertDoesNotThrow(() -> tagService.delete(1));
        verify(tagRepository).deleteById(1);
    }

    @Test
    void throwExceptionByDeleteIfTagIdNotExist() {
        doReturn(Optional.empty())
                .when(tagRepository).findById(1);
        assertThrows(EntityNotFoundException.class, () -> tagService.delete(1));
        verify(tagRepository, never()).deleteById(1);
    }
}
