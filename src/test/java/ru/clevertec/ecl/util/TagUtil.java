package ru.clevertec.ecl.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entity.Tag;

import java.util.Arrays;
import java.util.List;

public class TagUtil {

    public static Tag tagWithId1() {
        return new Tag(1, "new");
    }

    public static Tag tagWithoutId() {
        return new Tag(null, "new");
    }

    public static Tag tagWithId2() {
        return new Tag(2, "old");
    }

    public static Tag tagWithId3() {
        return new Tag(3, "expensive");
    }

    public static Tag tagWithId4() {
        return new Tag(4, "cheap");
    }

    public static Tag tagWithId5() {
        return new Tag(5, "short");
    }

    public static Tag tagWithId6() {
        return new Tag(6, "long");
    }

    public static Tag tagForSaveWithId() {
        return new Tag(7, "hot");
    }

    public static Tag tagForSaveWithoutId() {
        return new Tag(null, "hot");
    }

    public static Tag tagForUpdateWithId() {
        return new Tag(1, "hot");
    }

    public static Tag tagForUpdateWithoutId() {
        return new Tag(null, "hot");
    }

    public static TagDto tagDtoWithId1() {
        return new TagDto(1, "new");
    }

    public static TagDto tagDtoWithoutId() {
        return new TagDto(null, "new");
    }

    public static TagDto tagDtoWithId2() {
        return new TagDto(2, "old");
    }

    public static TagDto tagDtoWithId3() {
        return new TagDto(3, "expensive");
    }

    public static TagDto tagDtoWithId4() {
        return new TagDto(4, "cheap");
    }

    public static TagDto tagDtoWithId5() {
        return new TagDto(5, "short");
    }

    public static TagDto tagDtoWithId6() {
        return new TagDto(6, "long");
    }

    public static TagDto tagDtoWithId20() {
        return new TagDto(2, "short");
    }

    public static TagDto tagDtoWithoutId20() {
        return new TagDto(null, "short");
    }

    public static TagDto tagDtoForSaveWithId() {
        return new TagDto(7, "hot");
    }

    public static TagDto tagDtoForSaveWithoutId() {
        return new TagDto(null, "hot");
    }

    public static TagDto tagDtoForUpdateWithId() {
        return new TagDto(1, "hot");
    }

    public static TagDto tagDtoForUpdateWithoutId() {
        return new TagDto(null, "hot");
    }

    public static List<Tag> tags() {
        return Arrays.asList(tagWithId1(), tagWithId2(), tagWithId3(),
                tagWithId4(), tagWithId5(), tagWithId6());
    }

    public static List<TagDto> dtoTags() {
        return Arrays.asList(tagDtoWithId1(), tagDtoWithId2(), tagDtoWithId3(),
                tagDtoWithId4(), tagDtoWithId5(), tagDtoWithId6());
    }

    public static Pageable pageable() {
        return PageRequest.of(0, 20);
    }
}
