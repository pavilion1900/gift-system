package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/certificates")
@RequiredArgsConstructor
@Validated
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping
    public ResponseEntity<List<CertificateDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(certificateService.findAll(pageable));
    }

    @GetMapping("/param")
    public ResponseEntity<List<CertificateDto>> findAllBy(
            @Pattern(regexp = "[A-Z, a-z]+") @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            Pageable pageable) {
        return ResponseEntity.ok(certificateService.findAllByIgnoreCase(name, description, pageable));
    }

    @GetMapping("/tag/{tagName}")
    public ResponseEntity<List<CertificateDto>> findByTagName(@NotBlank @PathVariable String tagName,
                                                              Pageable pageable) {
        return ResponseEntity.ok(certificateService.findAllByTagName(tagName, pageable));
    }

    @GetMapping("/tags/")
    public ResponseEntity<List<CertificateDto>> findAllBySeveralTagNames(
            @RequestParam(required = false) List<String> tagNames,
            Pageable pageable) {
        return ResponseEntity.ok(certificateService.findAllBySeveralTagNames(tagNames, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> findById(@Positive @PathVariable Integer id) {
        return ResponseEntity.ok(certificateService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CertificateDto> save(@Valid @RequestBody CertificateDto certificateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(certificateService.save(certificateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificateDto> update(@Positive @PathVariable Integer id,
                                                 @Valid @RequestBody CertificateDto certificateDto) {
        return ResponseEntity.ok(certificateService.update(id, certificateDto));
    }

    @PatchMapping("/price/{id}")
    public ResponseEntity<CertificateDto> updatePrice(@Positive @PathVariable Integer id,
                                                      @Valid @RequestBody CertificatePriceDto certificatePriceDto) {
        return ResponseEntity.ok(certificateService.updatePrice(id, certificatePriceDto));
    }

    @PatchMapping("/duration/{id}")
    public ResponseEntity<CertificateDto> updatePrice(
            @Positive @PathVariable Integer id,
            @Valid @RequestBody CertificateDurationDto certificateDurationDto) {
        return ResponseEntity.ok(certificateService.updateDuration(id, certificateDurationDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Positive @PathVariable Integer id) {
        certificateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
