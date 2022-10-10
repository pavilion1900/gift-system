package ru.clevertec.ecl.service;

import ru.clevertec.ecl.dto.TagDto;

public interface TagService extends Service<TagDto> {

    TagDto findByName(String tagName);

    TagDto findMostWidelyUsedTag();

    TagDto saveOrUpdate(TagDto tagDto);
}
