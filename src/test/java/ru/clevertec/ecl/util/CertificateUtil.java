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

    private static final Tag TAG = new Tag(1, "new");
    private static final Tag TAG_WITHOUT_ID = new Tag(null, "new");
    private static final Tag TAG_3 = new Tag(3, "expensive");
    private static final Tag TAG_4 = new Tag(4, "cheap");
    private static final Tag TAG_5 = new Tag(5, "short");
    private static final Tag TAG_5_WITHOUT_ID = new Tag(null, "short");
    private static final Tag TAG_6 = new Tag(6, "long");
    private static final TagDto TAG_DTO = new TagDto(1, "new");
    private static final TagDto TAG_DTO_WITHOUT_ID = new TagDto(null, "new");
    private static final TagDto TAG_DTO_3 = new TagDto(3, "expensive");
    private static final TagDto TAG_DTO_4 = new TagDto(4, "cheap");
    private static final TagDto TAG_DTO_5 = new TagDto(5, "short");
    private static final TagDto TAG_DTO_5_WITHOUT_ID = new TagDto(null, "short");
    private static final TagDto TAG_DTO_6 = new TagDto(6, "long");

    public static Certificate certificateWithId1() {
        return new Certificate(1, "first", "first description",
                BigDecimal.valueOf(10.23), 10, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG, TAG_5));
    }

    public static Certificate certificateWithoutId() {
        return new Certificate(null, "first", "first description",
                BigDecimal.valueOf(10.23), 10, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG_WITHOUT_ID, TAG_5_WITHOUT_ID));
    }

    public static Certificate certificateWithId2() {
        return new Certificate(2, "second", "second description",
                BigDecimal.valueOf(15.85), 20, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG, TAG_6));
    }

    public static Certificate certificateWithId3() {
        return new Certificate(3, "third", "third description",
                BigDecimal.valueOf(20.83), 8, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG, TAG_3, TAG_5));
    }

    public static Certificate certificateWithId4() {
        return new Certificate(4, "fourth", "fourth description",
                BigDecimal.valueOf(25.58), 15, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG, TAG_3));
    }

    public static Certificate certificateWithId5() {
        return new Certificate(5, "fifth", "fifth description",
                BigDecimal.valueOf(5.27), 30, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG, TAG_4, TAG_6));
    }

    public static Certificate certificateForUpdateWithId() {
        return new Certificate(1, "sixth", "first description",
                BigDecimal.valueOf(10.23), 10, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG, TAG_5));
    }

    public static Certificate certificateForSaveWithId() {
        return new Certificate(6, "sixth", "sixth description",
                BigDecimal.valueOf(10.23), 10, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG, TAG_5));
    }

    public static Certificate certificateForSaveWithoutId() {
        return new Certificate(null, "sixth", "sixth description",
                BigDecimal.valueOf(10.23), 10, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG, TAG_5));
    }

    public static CertificateDto certificateDtoWithId1() {
        return new CertificateDto(1, "first", "first description",
                BigDecimal.valueOf(10.23), 10, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG_DTO, TAG_DTO_5));
    }

    public static CertificateDto certificateDtoWithoutId() {
        return new CertificateDto(null, "first", "first description",
                BigDecimal.valueOf(10.23), 10, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG_DTO_WITHOUT_ID, TAG_DTO_5_WITHOUT_ID));
    }

    public static CertificateDto certificateDtoWithId2() {
        return new CertificateDto(2, "second", "second description",
                BigDecimal.valueOf(15.85), 20, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG_DTO, TAG_DTO_6));
    }

    public static CertificateDto certificateDtoWithId3() {
        return new CertificateDto(3, "third", "third description",
                BigDecimal.valueOf(20.83), 8, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG_DTO, TAG_DTO_3, TAG_DTO_5));
    }

    public static CertificateDto certificateDtoWithId4() {
        return new CertificateDto(4, "fourth", "fourth description",
                BigDecimal.valueOf(25.58), 15, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG_DTO, TAG_DTO_3));
    }

    public static CertificateDto certificateDtoWithId5() {
        return new CertificateDto(5, "fifth", "fifth description",
                BigDecimal.valueOf(5.27), 30, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG_DTO, TAG_DTO_4, TAG_DTO_6));
    }

    public static CertificateDto certificateDtoForSaveWithId() {
        return new CertificateDto(6, "sixth", "sixth description",
                BigDecimal.valueOf(10.23), 10, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG_DTO, TAG_DTO_5));
    }

    public static CertificateDto certificateDtoForSaveWithoutId() {
        return new CertificateDto(null, "sixth", "sixth description",
                BigDecimal.valueOf(10.23), 10, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG_DTO, TAG_DTO_5));
    }

    public static CertificateDto certificateDtoForUpdateWithId() {
        return new CertificateDto(1, "sixth", "first description",
                BigDecimal.valueOf(10.23), 10, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG_DTO, TAG_DTO_5));
    }

    public static CertificateDto certificateDtoForUpdateWithoutId() {
        return new CertificateDto(null, "sixth", "sixth description",
                BigDecimal.valueOf(10.23), 10, LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(TAG_DTO, TAG_DTO_5));
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
