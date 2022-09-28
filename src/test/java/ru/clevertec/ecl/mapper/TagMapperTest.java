package ru.clevertec.ecl.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entity.Tag;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.clevertec.ecl.util.TagUtil.tagDtoWithId1;
import static ru.clevertec.ecl.util.TagUtil.tagWithId1;

class TagMapperTest {

    private final TagMapper mapper = Mappers.getMapper(TagMapper.class);

    @Test
    void checkToDto() {
        TagDto actual = mapper.toDto(tagWithId1());
        assertThat(actual).isEqualTo(tagDtoWithId1());
    }

    @Test
    void checkToEntity() {
        Tag actual = mapper.toEntity(tagDtoWithId1());
        assertThat(actual).isEqualTo(tagWithId1());
    }
}
