package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.CertificateDurationDto;
import ru.clevertec.ecl.dto.CertificatePriceDto;

import java.util.List;

public interface CertificateService {

    List<CertificateDto> findAll(Pageable pageable);

    List<CertificateDto> findAllByIgnoreCase(String name, String description, Pageable pageable);

    List<CertificateDto> findAllByTagName(String tagName, Pageable pageable);

    List<CertificateDto> findAllBySeveralTagNames(List<String> tagNames, Pageable pageable);

    CertificateDto findById(Integer id);

    CertificateDto findByNameIgnoreCase(String certificateName);

    Integer findLastSequenceValue();

    CertificateDto save(CertificateDto certificateDto);

    CertificateDto update(Integer id, CertificateDto certificateDto);

    CertificateDto updatePrice(Integer id, CertificatePriceDto certificatePriceDto);

    CertificateDto updateDuration(Integer id, CertificateDurationDto certificateDurationDto);

    void delete(Integer id);
}
