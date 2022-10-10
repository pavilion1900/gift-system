package ru.clevertec.ecl.util;

import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entity.Certificate;
import ru.clevertec.ecl.entity.Tag;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CertificateUtil {

    private static final Tag TAG = Tag.builder()
            .id(1)
            .name("new")
            .build();
    private static final Tag TAG_WITHOUT_ID = Tag.builder()
            .name("new")
            .build();
    private static final Tag TAG_3 = Tag.builder()
            .id(3)
            .name("expensive")
            .build();
    private static final Tag TAG_4 = Tag.builder()
            .id(4)
            .name("cheap")
            .build();
    private static final Tag TAG_5 = Tag.builder()
            .id(5)
            .name("short")
            .build();
    private static final Tag TAG_5_WITHOUT_ID = Tag.builder()
            .name("short")
            .build();
    private static final Tag TAG_6 = Tag.builder()
            .id(6)
            .name("long")
            .build();
    private static final TagDto TAG_DTO = TagDto.builder()
            .id(1)
            .name("new")
            .build();
    private static final TagDto TAG_DTO_WITHOUT_ID = TagDto.builder()
            .name("new")
            .build();
    private static final TagDto TAG_DTO_3 = TagDto.builder()
            .id(3)
            .name("expensive")
            .build();
    private static final TagDto TAG_DTO_4 = TagDto.builder()
            .id(4)
            .name("cheap")
            .build();
    private static final TagDto TAG_DTO_5 = TagDto.builder()
            .id(5)
            .name("short")
            .build();
    private static final TagDto TAG_DTO_5_WITHOUT_ID = TagDto.builder()
            .name("short")
            .build();
    private static final TagDto TAG_DTO_6 = TagDto.builder()
            .id(6)
            .name("long")
            .build();

    public static Certificate certificateWithId1() {
        return Certificate.builder()
                .id(1)
                .name("first")
                .description("first description")
                .price(BigDecimal.valueOf(10.23))
                .duration(10)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG, TAG_5))
                .build();
    }

    public static Certificate certificateWithoutId() {
        return Certificate.builder()
                .name("first")
                .description("first description")
                .price(BigDecimal.valueOf(10.23))
                .duration(10)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG_WITHOUT_ID, TAG_5_WITHOUT_ID))
                .build();
    }

    public static Certificate certificateWithId2() {
        return Certificate.builder()
                .id(2)
                .name("second")
                .description("second description")
                .price(BigDecimal.valueOf(15.85))
                .duration(20)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG, TAG_6))
                .build();
    }

    public static Certificate certificateWithId3() {
        return Certificate.builder()
                .id(3)
                .name("third")
                .description("third description")
                .price(BigDecimal.valueOf(20.83))
                .duration(8)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG, TAG_3, TAG_5))
                .build();
    }

    public static Certificate certificateWithId4() {
        return Certificate.builder()
                .id(4)
                .name("fourth")
                .description("fourth description")
                .price(BigDecimal.valueOf(25.58))
                .duration(15)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG, TAG_3))
                .build();
    }

    public static Certificate certificateWithId5() {
        return Certificate.builder()
                .id(5)
                .name("fifth")
                .description("fifth description")
                .price(BigDecimal.valueOf(5.27))
                .duration(30)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG, TAG_4, TAG_6))
                .build();
    }

    public static Certificate certificateForUpdateWithId() {
        return Certificate.builder()
                .id(1)
                .name("sixth")
                .description("first description")
                .price(BigDecimal.valueOf(10.23))
                .duration(10)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG, TAG_5))
                .build();
    }

    public static Certificate certificateUpdatedDuration() {
        return Certificate.builder()
                .id(1)
                .name("first")
                .description("first description")
                .price(BigDecimal.valueOf(10.23))
                .duration(20)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG, TAG_5))
                .build();
    }

    public static Certificate certificateForSaveWithId() {
        return Certificate.builder()
                .id(6)
                .name("sixth")
                .description("sixth description")
                .price(BigDecimal.valueOf(10.23))
                .duration(10)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG, TAG_5))
                .build();
    }

    public static Certificate certificateForSaveWithoutId() {
        return Certificate.builder()
                .name("sixth")
                .description("sixth description")
                .price(BigDecimal.valueOf(10.23))
                .duration(10)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG, TAG_5))
                .build();
    }

    public static CertificateDto certificateDtoWithId1() {
        return CertificateDto.builder()
                .id(1)
                .name("first")
                .description("first description")
                .price(BigDecimal.valueOf(10.23))
                .duration(10)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG_DTO, TAG_DTO_5))
                .build();
    }

    public static CertificateDto certificateDtoWithoutId() {
        return CertificateDto.builder()
                .name("first")
                .description("first description")
                .price(BigDecimal.valueOf(10.23))
                .duration(10)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG_DTO_WITHOUT_ID, TAG_DTO_5_WITHOUT_ID))
                .build();
    }

    public static CertificateDto certificateDtoWithId2() {
        return CertificateDto.builder()
                .id(2)
                .name("second")
                .description("second description")
                .price(BigDecimal.valueOf(15.85))
                .duration(20)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG_DTO, TAG_DTO_6))
                .build();
    }

    public static CertificateDto certificateDtoWithId3() {
        return CertificateDto.builder()
                .id(3)
                .name("third")
                .description("third description")
                .price(BigDecimal.valueOf(20.83))
                .duration(8)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG_DTO, TAG_DTO_3, TAG_DTO_5))
                .build();
    }

    public static CertificateDto certificateDtoWithId4() {
        return CertificateDto.builder()
                .id(4)
                .name("fourth")
                .description("fourth description")
                .price(BigDecimal.valueOf(25.58))
                .duration(15)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG_DTO, TAG_DTO_3))
                .build();
    }

    public static CertificateDto certificateDtoWithId5() {
        return CertificateDto.builder()
                .id(5)
                .name("fifth")
                .description("fifth description")
                .price(BigDecimal.valueOf(5.27))
                .duration(30)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG_DTO, TAG_DTO_4, TAG_DTO_6))
                .build();
    }

    public static CertificateDto certificateDtoForSaveWithId() {
        return CertificateDto.builder()
                .id(6)
                .name("sixth")
                .description("sixth description")
                .price(BigDecimal.valueOf(10.23))
                .duration(10)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG_DTO, TAG_DTO_5))
                .build();
    }

    public static CertificateDto certificateDtoForSaveWithoutId() {
        return CertificateDto.builder()
                .name("sixth")
                .description("sixth description")
                .price(BigDecimal.valueOf(10.23))
                .duration(10)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG_DTO, TAG_DTO_5))
                .build();
    }

    public static CertificateDto certificateDtoForUpdateDuration() {
        return CertificateDto.builder()
                .duration(20)
                .build();
    }

    public static CertificateDto certificateDtoUpdatedDuration() {
        return CertificateDto.builder()
                .id(1)
                .name("first")
                .description("first description")
                .price(BigDecimal.valueOf(10.23))
                .duration(20)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG_DTO, TAG_DTO_5))
                .build();
    }

    public static CertificateDto certificateDtoForUpdateWithoutId() {
        return CertificateDto.builder()
                .name("sixth")
                .description("sixth description")
                .price(BigDecimal.valueOf(10.23))
                .duration(10)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(Arrays.asList(TAG_DTO, TAG_DTO_5))
                .build();
    }

    public static List<Certificate> certificates() {
        return Arrays.asList(certificateWithId1(), certificateWithId2(), certificateWithId3(),
                certificateWithId4(), certificateWithId5());
    }

    public static List<CertificateDto> dtoCertificates() {
        return Arrays.asList(certificateDtoWithId1(), certificateDtoWithId2(),
                certificateDtoWithId3(), certificateDtoWithId4(), certificateDtoWithId5());
    }

    public static Pageable pageable() {
        return PageRequest.of(0, 20);
    }

    public static Pageable pageWithSizeOne() {
        return PageRequest.of(0, 1);
    }

    public static ExampleMatcher matcher() {
        return ExampleMatcher.matchingAny()
                .withMatcher("name",
                        ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("description",
                        ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
    }
}
