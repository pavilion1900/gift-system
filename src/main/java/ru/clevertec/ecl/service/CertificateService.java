package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.CertificateDto;

import java.util.List;

public interface CertificateService extends Service<CertificateDto> {

    List<CertificateDto> findAllBy(String name, String description, Pageable pageable);

    List<CertificateDto> findAllByTagName(String tagName, Pageable pageable);
}
