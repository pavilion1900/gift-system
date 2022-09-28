package ru.clevertec.ecl.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.integration.IntegrationTestBase;
import ru.clevertec.ecl.service.TagService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.clevertec.ecl.util.TagUtil.dtoTags;
import static ru.clevertec.ecl.util.TagUtil.tagDtoForSaveWithId;
import static ru.clevertec.ecl.util.TagUtil.tagDtoForSaveWithoutId;
import static ru.clevertec.ecl.util.TagUtil.tagDtoForUpdateWithId;
import static ru.clevertec.ecl.util.TagUtil.tagDtoForUpdateWithoutId;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithId1;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithoutId;

@RequiredArgsConstructor
public class TagServiceImplTest extends IntegrationTestBase {

    private final TagService service;

    @Test
    void checkFindAll() {
        List<TagDto> actual = service.findAll(PageRequest.of(0, 20));
        assertEquals(dtoTags(), actual);
    }

    @Test
    void checkFindByIdIfTagIdExist() {
        TagDto actual = service.findById(1);
        assertEquals(tagDtoWithId1(), actual);
    }

    @Test
    void throwExceptionIfTagIdNotExist() {
        assertThrows(EntityNotFoundException.class, () -> service.findById(10));
    }

    @Test
    void checkFindByNameIfTagNameExist() {
        TagDto actual = service.findByName("new");
        assertEquals(tagDtoWithId1(), actual);
    }

    @Test
    void throwExceptionIfTagNameNotExist() {
        assertThrows(EntityNotFoundException.class, () -> service.findByName("short333"));
    }

    @Test
    void checkSaveIfTagHasUniqueName() {
        TagDto actual = service.save(tagDtoForSaveWithoutId());
        assertEquals(tagDtoForSaveWithId(), actual);
    }

    @Test
    void throwExceptionBySaveIfTagHasNotUniqueName() {
        assertThrows(EntityNotFoundException.class, () -> service.save(tagDtoWithoutId()));
    }

    @Test
    void checkUpdateIfTagHasUniqueIdAndUniqueName() {
        TagDto actual = service.update(1, tagDtoForUpdateWithoutId());
        TagDto expected = tagDtoForUpdateWithId();
        assertEquals(expected, actual);
    }

    @Test
    void throwExceptionByUpdateIfTagIdNotExist() {
        assertThrows(EntityNotFoundException.class,
                () -> service.update(10, tagDtoForUpdateWithoutId()));
    }

    @Test
    void throwExceptionByUpdateIfTagHasUniqueIdAndNotUniqueName() {
        assertThrows(EntityNotFoundException.class,
                () -> service.update(2, tagDtoWithoutId()));
    }

    @Test
    void checkDeleteIfTagHasUniqueId() {
        service.delete(1);
        assertThrows(EntityNotFoundException.class, () -> service.findById(1));
    }

    @Test
    void throwExceptionByDeleteIfTagIdNotExist() {
        assertThrows(EntityNotFoundException.class, () -> service.delete(10));
    }
}
