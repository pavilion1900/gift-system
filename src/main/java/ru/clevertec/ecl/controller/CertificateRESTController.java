package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.service.CertificateService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/certificates")
@RequiredArgsConstructor
public class CertificateRESTController {

    private final CertificateService service;

    @GetMapping
    public ResponseEntity<List<CertificateDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/param")
    public ResponseEntity<List<CertificateDto>> findAllBy(
            HttpServletRequest request, Pageable pageable) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        return ResponseEntity.ok(service.findAllBy(name, description, pageable));
    }

    @GetMapping("/tag/{tagName}")
    public ResponseEntity<List<CertificateDto>> findByTagName(
            @PathVariable String tagName, Pageable pageable) {
        return ResponseEntity.ok(service.findAllByTagName(tagName, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> findById(@Positive @PathVariable int id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CertificateDto> save(@Valid @RequestBody CertificateDto certificate) {
        return ResponseEntity.ok(service.save(certificate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificateDto> update(
            @Positive @PathVariable Integer id,
            @RequestBody CertificateDto certificateDto) {
        return ResponseEntity.ok(service.update(id, certificateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Positive @PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
