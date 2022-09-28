package ru.clevertec.ecl.integration.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.integration.IntegrationTestBase;
import ru.clevertec.ecl.repository.TagRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static ru.clevertec.ecl.util.TagUtil.tagForSaveWithId;
import static ru.clevertec.ecl.util.TagUtil.tagForSaveWithoutId;
import static ru.clevertec.ecl.util.TagUtil.tagForUpdateWithId;
import static ru.clevertec.ecl.util.TagUtil.tagWithId1;
import static ru.clevertec.ecl.util.TagUtil.tags;

@RequiredArgsConstructor
public class TagRepositoryTest extends IntegrationTestBase {

    private final TagRepository repository;

    @Test
    void checkFindAll() {
        List<Tag> actual = repository.findAll(PageRequest.of(0, 20)).getContent();
        assertEquals(tags(), actual);
    }

    @Test
    void checkFindByIdIfTagIdExist() {
        Optional<Tag> optional = repository.findById(1);
        optional.ifPresent(tag -> assertEquals(tagWithId1(), tag));
    }

    @Test
    void checkFindByNameIfTagNameExist() {
        Optional<Tag> optional = repository.findByNameIgnoreCase("new");
        optional.ifPresent(tag -> assertEquals(tagWithId1(), tag));
    }

    @Test
    void checkSaveIfTagHasUniqueName() {
        Tag actual = repository.save(tagForSaveWithoutId());
        assertEquals(tagForSaveWithId(), actual);
    }

    @Test
    void checkUpdateIfTagHasUniqueIdAndUniqueName() {
        Tag actual = repository.save(tagForUpdateWithId());
        assertEquals(tagForUpdateWithId(), actual);
    }

    @Test
    void checkDeleteIfTagHasUniqueId() {
        repository.deleteById(1);
        Optional<Tag> optional = repository.findById(1);
        assertFalse(optional.isPresent());
    }
}
