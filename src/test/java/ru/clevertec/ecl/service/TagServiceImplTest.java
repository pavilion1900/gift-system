package ru.clevertec.ecl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.clevertec.ecl.util.TagUtil.dtoTags;
import static ru.clevertec.ecl.util.TagUtil.pageable;
import static ru.clevertec.ecl.util.TagUtil.tagDtoForUpdateWithId;
import static ru.clevertec.ecl.util.TagUtil.tagDtoForUpdateWithoutId;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithId1;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithId2;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithId3;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithId4;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithId5;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithId6;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithoutId;
import static ru.clevertec.ecl.util.TagUtil.tagForUpdateWithId;
import static ru.clevertec.ecl.util.TagUtil.tagWithId1;
import static ru.clevertec.ecl.util.TagUtil.tagWithId2;
import static ru.clevertec.ecl.util.TagUtil.tagWithId3;
import static ru.clevertec.ecl.util.TagUtil.tagWithId4;
import static ru.clevertec.ecl.util.TagUtil.tagWithId5;
import static ru.clevertec.ecl.util.TagUtil.tagWithId6;
import static ru.clevertec.ecl.util.TagUtil.tagWithoutId;
import static ru.clevertec.ecl.util.TagUtil.tags;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository repository;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagServiceImpl service;

    @Test
    void checkFindAll() {
        doReturn(new PageImpl<>(tags()))
                .when(repository).findAll(pageable());
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
        List<TagDto> actual = service.findAll(pageable());
        List<TagDto> expected = new ArrayList<>(dtoTags());
        assertEquals(expected, actual);
        verify(repository).findAll(pageable());
        verify(tagMapper, times(6)).toDto(any(Tag.class));
    }

    @Test
    void checkFindByIdIfTagIdExist() {
        doReturn(Optional.of(tagWithId1()))
                .when(repository).findById(1);
        doReturn(tagDtoWithId1())
                .when(tagMapper).toDto(tagWithId1());
        TagDto actual = service.findById(1);
        TagDto expected = tagDtoWithId1();
        assertEquals(expected, actual);
        verify(repository).findById(1);
        verify(tagMapper).toDto(tagWithId1());
    }

    @Test
    void throwExceptionIfTagIdNotExist() {
        doReturn(Optional.empty())
                .when(repository).findById(1);
        assertThrows(EntityNotFoundException.class, () -> service.findById(1));
    }

    @Test
    void checkFindByNameIfTagNameExist() {
        doReturn(Optional.of(tagWithId1()))
                .when(repository).findByNameIgnoreCase("short");
        doReturn(tagDtoWithId1())
                .when(tagMapper).toDto(tagWithId1());
        TagDto actual = service.findByName("short");
        TagDto expected = tagDtoWithId1();
        assertEquals(expected, actual);
        verify(repository).findByNameIgnoreCase("short");
        verify(tagMapper).toDto(tagWithId1());
    }

    @Test
    void throwExceptionIfTagNameNotExist() {
        doReturn(Optional.empty())
                .when(repository).findByNameIgnoreCase("short");
        assertThrows(EntityNotFoundException.class, () -> service.findByName("short"));
    }

    @Test
    void checkFindMostWidelyUsedTag() {
        doReturn(Optional.of(tagWithId1()))
                .when(repository).findMostWidelyUsedTag();
        doReturn(tagDtoWithId1())
                .when(tagMapper).toDto(tagWithId1());
        TagDto actual = service.findMostWidelyUsedTag();
        TagDto expected = tagDtoWithId1();
        assertEquals(expected, actual);
        verify(repository).findMostWidelyUsedTag();
        verify(tagMapper).toDto(tagWithId1());
    }

    @Test
    void checkSaveIfTagHasUniqueName() {
        doReturn(tagWithoutId())
                .when(tagMapper).toEntity(tagDtoWithoutId());
        doReturn(tagWithId1())
                .when(repository).save(tagWithoutId());
        doReturn(tagDtoWithId1())
                .when(tagMapper).toDto(tagWithId1());
        TagDto actual = service.save(tagDtoWithoutId());
        TagDto expected = tagDtoWithId1();
        assertEquals(expected, actual);
        verify(tagMapper).toEntity(tagDtoWithoutId());
        verify(repository).save(tagWithoutId());
        verify(tagMapper).toDto(tagWithId1());
    }

    @Test
    void throwExceptionBySaveIfTagHasNotUniqueName() {
        doReturn(Optional.of(tagWithId1()))
                .when(repository).findByNameIgnoreCase(tagDtoWithoutId().getName());
        assertThrows(EntityNotFoundException.class, () -> service.save(tagDtoWithoutId()));
    }

    @Test
    void checkUpdateIfTagHasUniqueIdAndUniqueName() {
        doReturn(Optional.of(tagWithId1()))
                .when(repository).findById(1);
        doReturn(tagForUpdateWithId())
                .when(repository).save(tagForUpdateWithId());
        doReturn(tagDtoForUpdateWithId())
                .when(tagMapper).toDto(tagForUpdateWithId());
        TagDto actual = service.update(1, tagDtoForUpdateWithoutId());
        TagDto expected = tagDtoForUpdateWithId();
        assertEquals(expected, actual);
        verify(repository).findById(1);
        verify(repository).save(tagForUpdateWithId());
        verify(tagMapper).toDto(tagForUpdateWithId());
    }

    @Test
    void throwExceptionByUpdateIfTagIdNotExist() {
        doReturn(Optional.empty())
                .when(repository).findById(1);
        assertThrows(EntityNotFoundException.class, () -> service.update(1, tagDtoWithoutId()));
        verify(repository, never()).save(tagWithId1());
    }

    @Test
    void throwExceptionByUpdateIfTagHasUniqueIdAndNotUniqueName() {
        Tag tagWithId2 = new Tag(2, "old");
        doReturn(Optional.of(tagWithId2))
                .when(repository).findById(2);
        doReturn(Optional.of(tagWithId1()))
                .when(repository).findByNameIgnoreCase("new");
        assertThrows(EntityNotFoundException.class, () -> service.update(2, tagDtoWithoutId()));
        verify(repository, never()).save(tagWithId1());
    }

    @Test
    void checkDeleteIfTagHasUniqueId() {
        doReturn(Optional.of(tagWithId1()))
                .when(repository).findById(1);
        assertDoesNotThrow(() -> service.delete(1));
        verify(repository).deleteById(1);
    }

    @Test
    void throwExceptionByDeleteIfTagIdNotExist() {
        doReturn(Optional.empty())
                .when(repository).findById(1);
        assertThrows(EntityNotFoundException.class, () -> service.delete(1));
        verify(repository, never()).deleteById(1);
    }
}
