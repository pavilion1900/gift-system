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
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository repository;
    private final TagMapper tagMapper;

    @Override
    public List<TagDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).stream()
                .map(tagMapper::toDto)
                .collect(toList());
    }

    @Override
    public TagDto findById(Integer id) {
        return repository.findById(id)
                .map(tagMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Tag with id %d not found", id)
                ));
    }

    @Override
    public TagDto findByName(String tagName) {
        return repository.findByNameIgnoreCase(tagName)
                .map(tagMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Tag with name %s not found", tagName)
                ));
    }

    @Override
    @Transactional
    public TagDto save(TagDto tagDto) {
        return (TagDto) repository.findByNameIgnoreCase(tagDto.getName())
                .map(tag -> {
                    throw new EntityNotFoundException(
                            String.format("Tag with name %s already exist", tagDto.getName()));
                })
                .orElseGet(() -> {
                    Tag tag = tagMapper.toEntity(tagDto);
                    return tagMapper.toDto(repository.save(tag));
                });
    }

    @Override
    @Transactional
    public TagDto update(Integer id, TagDto tagDto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Tag with id %d not exist", id)));
        checkTagNameAndId(tagDto, id);
        Tag tag = tagMapper.toEntity(tagDto);
        tag.setId(id);
        return tagMapper.toDto(repository.save(tag));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        repository.findById(id)
                .map(tag -> {
                    repository.deleteById(id);
                    return tag;
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Tag with id %d not exist", id)));
    }

    private void checkTagNameAndId(TagDto tagDto, Integer id) {
        Optional<Tag> optionalTag =
                repository.findByNameIgnoreCase(tagDto.getName());
        if (optionalTag.isPresent() && !optionalTag.get().getId().equals(id)) {
            throw new EntityNotFoundException(
                    String.format("Tag with name %s already exist", tagDto.getName()));
        }
    }

    @Override
    public TagDto saveOrUpdate(TagDto tagDto) {
        return repository.findByNameIgnoreCase(tagDto.getName())
                .map(tagMapper::toDto)
                .orElseGet(() -> {
                    Tag tag = tagMapper.toEntity(tagDto);
                    return tagMapper.toDto(repository.save(tag));
                });
    }
}
