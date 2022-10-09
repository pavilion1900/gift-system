package ru.clevertec.ecl.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entity.Tag;

import java.util.Arrays;
import java.util.List;

public class TagUtil {

    public static Tag tagWithId1() {
        return Tag.builder()
                .id(1)
                .name("new")
                .build();
    }

    public static Tag tagWithoutId() {
        return Tag.builder()
                .name("new")
                .build();
    }

    public static Tag tagWithId2() {
        return Tag.builder()
                .id(2)
                .name("old")
                .build();
    }

    public static Tag tagWithId3() {
        return Tag.builder()
                .id(3)
                .name("expensive")
                .build();
    }

    public static Tag tagWithId4() {
        return Tag.builder()
                .id(4)
                .name("cheap")
                .build();
    }

    public static Tag tagWithId5() {
        return Tag.builder()
                .id(5)
                .name("short")
                .build();
    }

    public static Tag tagWithId6() {
        return Tag.builder()
                .id(6)
                .name("long")
                .build();
    }

    public static Tag tagForSaveWithId() {
        return Tag.builder()
                .id(7)
                .name("hot")
                .build();
    }

    public static Tag tagForSaveWithoutId() {
        return Tag.builder()
                .name("hot")
                .build();
    }

    public static Tag tagForUpdateWithId() {
        return Tag.builder()
                .id(1)
                .name("hot")
                .build();
    }

    public static Tag tagForUpdateWithoutId() {
        return Tag.builder()
                .name("hot")
                .build();
    }

    public static TagDto tagDtoWithId1() {
        return TagDto.builder()
                .id(1)
                .name("new")
                .build();
    }

    public static TagDto tagDtoWithoutId() {
        return TagDto.builder()
                .name("new")
                .build();
    }

    public static TagDto tagDtoWithId2() {
        return TagDto.builder()
                .id(2)
                .name("old")
                .build();
    }

    public static TagDto tagDtoWithId3() {
        return TagDto.builder()
                .id(3)
                .name("expensive")
                .build();
    }

    public static TagDto tagDtoWithId4() {
        return TagDto.builder()
                .id(4)
                .name("cheap")
                .build();
    }

    public static TagDto tagDtoWithId5() {
        return TagDto.builder()
                .id(5)
                .name("short")
                .build();
    }

    public static TagDto tagDtoWithId6() {
        return TagDto.builder()
                .id(6)
                .name("long")
                .build();
    }

    public static TagDto tagDtoWithId20() {
        return TagDto.builder()
                .id(2)
                .name("short")
                .build();
    }

    public static TagDto tagDtoWithoutId20() {
        return TagDto.builder()
                .name("short")
                .build();
    }

    public static TagDto tagDtoForSaveWithId() {
        return TagDto.builder()
                .id(7)
                .name("hot")
                .build();
    }

    public static TagDto tagDtoForSaveWithoutId() {
        return TagDto.builder()
                .name("hot")
                .build();
    }

    public static TagDto tagDtoForUpdateWithId() {
        return TagDto.builder()
                .id(1)
                .name("hot")
                .build();
    }

    public static TagDto tagDtoForUpdateWithoutId() {
        return TagDto.builder()
                .name("hot")
                .build();
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
