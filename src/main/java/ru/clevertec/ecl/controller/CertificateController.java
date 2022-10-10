package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.CertificateDurationDto;
import ru.clevertec.ecl.dto.CertificatePriceDto;
import ru.clevertec.ecl.service.CertificateService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/certificates")
@RequiredArgsConstructor
@Validated
public class CertificateController {

    private final CertificateService service;

    @GetMapping
    public ResponseEntity<List<CertificateDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/param")
    public ResponseEntity<List<CertificateDto>> findAllBy(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            Pageable pageable) {
        return ResponseEntity.ok(service.findAllByIgnoreCase(name, description, pageable));
    }

    @GetMapping("/tag/{tagName}")
    public ResponseEntity<List<CertificateDto>> findByTagName(
            @NotBlank @PathVariable String tagName,
            Pageable pageable) {
        return ResponseEntity.ok(service.findAllByTagName(tagName, pageable));
    }

    @GetMapping("/tags/")
    public ResponseEntity<List<CertificateDto>> findAllBySeveralTagNames(
            @RequestParam(required = false) List<String> tagNames, Pageable pageable) {
        return ResponseEntity.ok(service.findAllBySeveralTagNames(tagNames, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> findById(@Positive @PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CertificateDto> save(@Valid @RequestBody CertificateDto certificateDto) {
        return ResponseEntity.ok(service.save(certificateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificateDto> update(
            @Positive @PathVariable Integer id,
            @Valid @RequestBody CertificateDto certificateDto) {
        return ResponseEntity.ok(service.update(id, certificateDto));
    }

    @PatchMapping("/price/{id}")
    public ResponseEntity<CertificateDto> updatePrice(
            @Positive @PathVariable Integer id,
            @Valid @RequestBody CertificatePriceDto certificatePriceDto) {
        return ResponseEntity.ok(service.updatePrice(id, certificatePriceDto));
    }

    @PatchMapping("/duration/{id}")
    public ResponseEntity<CertificateDto> updatePrice(
            @Positive @PathVariable Integer id,
            @Valid @RequestBody CertificateDurationDto certificateDurationDto) {
        return ResponseEntity.ok(service.updateDuration(id, certificateDurationDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Positive @PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
