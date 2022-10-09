package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entity.Certificate;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.mapper.CertificateMapper;
import ru.clevertec.ecl.repository.CertificateRepository;
import ru.clevertec.ecl.service.CertificateService;
import ru.clevertec.ecl.service.TagService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final TagService tagService;
    private final CertificateMapper certificateMapper;

    @Override
    public List<CertificateDto> findAll(Pageable pageable) {
        return certificateRepository.findAll(pageable).stream()
                .map(certificateMapper::toDto)
                .collect(toList());
    }

    @Override
    public List<CertificateDto> findAllByIgnoreCase(String name, String description,
                                                    Pageable pageable) {
        return certificateRepository.findAll(
                        Example.of(Certificate.builder()
                                        .name(name)
                                        .description(description)
                                        .build(),
                                matcher()), pageable).stream()
                .map(certificateMapper::toDto)
                .collect(toList());
    }

    @Override
    public List<CertificateDto> findAllByTagName(String tagName, Pageable pageable) {
        return certificateRepository.findAllByTagName(tagName, pageable).stream()
                .map(certificateMapper::toDto)
                .collect(toList());
    }

    @Override
    public CertificateDto findById(Integer id) {
        return certificateRepository.findById(id)
                .map(certificateMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Certificate with id %d not found", id)
                ));
    }

    @Override
    public CertificateDto findByName(String certificateName) {
        return certificateRepository.findByNameIgnoreCase(certificateName)
                .map(certificateMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Certificate with name %s not found", certificateName)
                ));
    }

    @Override
    @Transactional
    public CertificateDto save(CertificateDto certificateDto) {
        checkCertificateName(certificateDto);
        checkTags(certificateDto);
        Certificate certificate = certificateMapper.toEntity(certificateDto);
        return certificateMapper.toDto(certificateRepository.save(certificate));
    }

    @Override
    @Transactional
    public CertificateDto update(Integer id, CertificateDto certificateDto) {
        CertificateDto certificateDtoWithId = findById(id);
        certificateMapper.updateDto(certificateDto, certificateDtoWithId);
        checkCertificateNameAndId(certificateDtoWithId, id);
        checkTags(certificateDtoWithId);
        Certificate certificate = certificateMapper.toEntity(certificateDtoWithId);
        return certificateMapper.toDto(certificateRepository.save(certificate));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        certificateRepository.findById(id)
                .map(certificate -> {
                    certificateRepository.deleteById(id);
                    return certificate;
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Certificate with id %d not exist", id)));
    }

    private void checkCertificateName(CertificateDto certificateDto) {
        if (certificateRepository.existsByNameIgnoreCase(certificateDto.getName())) {
            throw new EntityNotFoundException(
                    String.format("Certificate with name %s already exist",
                            certificateDto.getName()));
        }
    }

    private void checkCertificateNameAndId(CertificateDto certificateDto, Integer id) {
        Optional<Certificate> optionalCertificate =
                certificateRepository.findByNameIgnoreCase(certificateDto.getName());
        if (optionalCertificate.isPresent() && !optionalCertificate.get().getId().equals(id)) {
            throw new EntityNotFoundException(
                    String.format("Certificate with name %s already exist",
                            certificateDto.getName()));
        }
    }

    private void checkTags(CertificateDto certificateDto) {
        List<TagDto> tags = certificateDto.getTags();
        tags.forEach(tagDto -> {
            Integer id = tagService.saveOrUpdate(tagDto).getId();
            tagDto.setId(id);
        });
        certificateDto.setTags(tags);
    }

    private ExampleMatcher matcher() {
        return ExampleMatcher.matchingAny()
                .withMatcher("name",
                        ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("description",
                        ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
    }
}
