package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.service.TagService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/tags")
@RequiredArgsConstructor
@Validated
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(tagService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> findById(@Positive @PathVariable Integer id) {
        return ResponseEntity.ok(tagService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TagDto> findByName(@NotBlank @PathVariable String name) {
        return ResponseEntity.ok(tagService.findByNameIgnoreCase(name));
    }

    @GetMapping("/mostUsedTag")
    public ResponseEntity<TagDto> findMostWidelyUsedTag() {
        return ResponseEntity.ok(tagService.findMostWidelyUsedTag());
    }

    @GetMapping("/sequence")
    public ResponseEntity<Integer> findLastSequenceValue() {
        return ResponseEntity.ok(tagService.findLastSequenceValue());
    }

    @PostMapping
    public ResponseEntity<TagDto> save(@Valid @RequestBody TagDto tagDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.save(tagDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDto> update(@Positive @PathVariable Integer id,
                                         @Valid @RequestBody TagDto tagDto) {
        return ResponseEntity.ok(tagService.update(id, tagDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Positive @PathVariable Integer id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
