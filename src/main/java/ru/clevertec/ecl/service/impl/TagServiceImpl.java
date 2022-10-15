package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.service.TagService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public List<TagDto> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable).stream()
                .map(tagMapper::toDto)
                .collect(toList());
    }

    @Override
    public TagDto findById(Integer id) {
        return tagRepository.findById(id)
                .map(tagMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Tag with id %d not found", id)));
    }

    @Override
    public TagDto findByNameIgnoreCase(String tagName) {
        return tagRepository.findByNameIgnoreCase(tagName)
                .map(tagMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Tag with name %s not found", tagName)));
    }

    @Override
    public TagDto findMostWidelyUsedTag() {
        return tagRepository.findMostWidelyUsedTag()
                .map(tagMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));
    }

    @Override
    @Transactional
    public TagDto save(TagDto tagDto) {
        Tag tag = tagMapper.toEntity(tagDto);
        return tagMapper.toDto(tagRepository.save(tag));
    }

    @Override
    @Transactional
    public TagDto update(Integer id, TagDto tagDto) {
        return tagRepository.findById(id)
                .map(tag -> {
                    tag.setName(tagDto.getName());
                    return tag;
                })
                .map(tagRepository::save)
                .map(tagMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Tag with id %d not exist", id)));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        tagRepository.findById(id)
                .map(tag -> {
                    tagRepository.deleteById(id);
                    return tag;
                })
                .orElseThrow(() -> new EntityNotFoundException(String.format("Tag with id %d not exist", id)));
    }

    @Override
    @Transactional
    public TagDto saveOrUpdate(TagDto tagDto) {
        return tagRepository.findByNameIgnoreCase(tagDto.getName())
                .map(tagMapper::toDto)
                .orElseGet(() -> {
                    Tag tag = tagMapper.toEntity(tagDto);
                    return tagMapper.toDto(tagRepository.save(tag));
                });
    }
}
