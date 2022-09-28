package ru.clevertec.ecl.service;

import ru.clevertec.ecl.dto.TagDto;

public interface TagService extends Service<TagDto> {

    TagDto saveOrUpdate(TagDto tagDto);
}
