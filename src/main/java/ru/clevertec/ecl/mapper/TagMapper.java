package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entity.Tag;

@Mapper
public interface TagMapper {

    TagDto toDto(Tag tag);

    Tag toEntity(TagDto tagDto);
}
