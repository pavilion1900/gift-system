package ru.clevertec.ecl.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.integration.IntegrationTestBase;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.service.TagService;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.clevertec.ecl.testdata.TagUtil.dtoTags;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoForSaveWithId;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoForSaveWithoutId;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoForUpdateWithId;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoForUpdateWithoutId;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoWithId1;
import static ru.clevertec.ecl.testdata.TagUtil.tagDtoWithoutId;

@RequiredArgsConstructor
public class TagServiceImplTest extends IntegrationTestBase {

    private final TagService tagService;
    private final TagRepository tagRepository;

    @Test
    void checkFindAll() {
        List<TagDto> actual = tagService.findAll(PageRequest.of(0, 20));
        assertEquals(dtoTags(), actual);
    }

    @Test
    void checkFindByIdIfTagIdExist() {
        TagDto actual = tagService.findById(1);
        assertEquals(tagDtoWithId1(), actual);
    }

    @Test
    void throwExceptionIfTagIdNotExist() {
        assertThrows(EntityNotFoundException.class, () -> tagService.findById(10));
    }

    @Test
    void checkFindByNameIfTagNameExist() {
        TagDto actual = tagService.findByNameIgnoreCase("new");
        assertEquals(tagDtoWithId1(), actual);
    }

    @Test
    void throwExceptionIfTagNameNotExist() {
        assertThrows(EntityNotFoundException.class, () -> tagService.findByNameIgnoreCase("short333"));
    }

    static Stream<String> generateTagName() {
        return Stream.of(
                "new", "old", "expensive", "cheap", "short", "long"
        );
    }

    @ParameterizedTest
    @MethodSource("generateTagName")
    void checkFindByNameIfTagNameExist2(String tagName) {
        TagDto actual = tagService.findByNameIgnoreCase(tagName);
        assertNotNull(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"new11", "old22", "long33"})
    void checkFindByNameIfTagNameNotExist(String tagName) {
        assertThrows(EntityNotFoundException.class, () -> tagService.findByNameIgnoreCase(tagName));
    }

    @Test
    void checkFindMostWidelyUsedTag() {
        TagDto actual = tagService.findMostWidelyUsedTag();
        assertEquals(tagDtoWithId1(), actual);
    }

    @Test
    void checkSaveIfTagHasUniqueName() {
        TagDto actual = tagService.save(tagDtoForSaveWithoutId());
        assertEquals(tagDtoForSaveWithId(), actual);
    }

    @Test
    void throwExceptionBySaveIfTagHasNotUniqueName() {
        assertThrows(DataIntegrityViolationException.class, () -> tagService.save(tagDtoWithoutId()));
    }

    @Test
    void checkUpdateIfTagHasUniqueIdAndUniqueName() {
        TagDto actual = tagService.update(1, tagDtoForUpdateWithoutId());
        tagRepository.flush();
        assertEquals(tagDtoForUpdateWithId(), actual);
    }

    @Test
    void throwExceptionByUpdateIfTagIdNotExist() {
        assertThrows(EntityNotFoundException.class, () -> tagService.update(10, tagDtoForUpdateWithoutId()));
    }

    @Test
    void throwExceptionByUpdateIfTagHasUniqueIdAndNotUniqueName() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            tagService.update(2, tagDtoWithoutId());
            tagRepository.flush();
        });
    }

    @Test
    void checkDeleteIfTagHasUniqueId() {
        tagService.delete(2);
        tagRepository.flush();
        assertThrows(EntityNotFoundException.class, () -> tagService.findById(2));
    }

    @Test
    void throwExceptionByDeleteIfTagIdNotExist() {
        assertThrows(EntityNotFoundException.class, () -> tagService.delete(10));
    }
}
