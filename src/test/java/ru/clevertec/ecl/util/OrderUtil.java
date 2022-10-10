package ru.clevertec.ecl.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.clevertec.ecl.util.CertificateUtil.certificateWithId2;
import static ru.clevertec.ecl.util.CertificateUtil.certificateWithId3;
import static ru.clevertec.ecl.util.CertificateUtil.certificateWithId5;
import static ru.clevertec.ecl.util.UserUtil.userWithId1;
import static ru.clevertec.ecl.util.UserUtil.userWithId2;
import static ru.clevertec.ecl.util.UserUtil.userWithId3;

public class OrderUtil {

    public static Order orderWithId1() {
        return Order.builder()
                .id(1)
                .cost(BigDecimal.valueOf(20.83))
                .purchaseDate(LocalDateTime.now())
                .user(userWithId1())
                .certificate(certificateWithId3())
                .build();
    }

    public static Order orderWithId2() {
        return Order.builder()
                .id(2)
                .cost(BigDecimal.valueOf(5.27))
                .purchaseDate(LocalDateTime.now())
                .user(userWithId2())
                .certificate(certificateWithId5())
                .build();
    }

    public static Order orderWithId3() {
        return Order.builder()
                .id(3)
                .cost(BigDecimal.valueOf(15.85))
                .purchaseDate(LocalDateTime.now())
                .user(userWithId3())
                .certificate(certificateWithId2())
                .build();
    }

    public static Order orderWithId4() {
        return Order.builder()
                .id(4)
                .cost(BigDecimal.valueOf(5.27))
                .purchaseDate(LocalDateTime.now())
                .user(userWithId1())
                .certificate(certificateWithId5())
                .build();
    }

    public static Order orderWithoutId() {
        return Order.builder()
                .cost(BigDecimal.valueOf(20.83))
                .purchaseDate(LocalDateTime.now())
                .user(userWithId1())
                .certificate(certificateWithId3())
                .build();
    }

    public static OrderDto orderDtoWithId1() {
        return OrderDto.builder()
                .id(1)
                .cost(BigDecimal.valueOf(20.83))
                .purchaseDate(LocalDateTime.now())
                .userId(1)
                .certificateId(3)
                .build();
    }

    public static OrderDto orderDtoWithId2() {
        return OrderDto.builder()
                .id(2)
                .cost(BigDecimal.valueOf(5.27))
                .purchaseDate(LocalDateTime.now())
                .userId(2)
                .certificateId(5)
                .build();
    }

    public static OrderDto orderDtoWithId3() {
        return OrderDto.builder()
                .id(3)
                .cost(BigDecimal.valueOf(15.85))
                .purchaseDate(LocalDateTime.now())
                .userId(3)
                .certificateId(2)
                .build();
    }

    public static OrderDto orderDtoWithId4() {
        return OrderDto.builder()
                .id(4)
                .cost(BigDecimal.valueOf(5.27))
                .purchaseDate(LocalDateTime.now())
                .userId(1)
                .certificateId(5)
                .build();
    }

    public static OrderDto orderDtoForMakeOrder() {
        return OrderDto.builder()
                .userId(1)
                .certificateId(3)
                .build();
    }

    public static OrderDto orderDtoForMakeOrderWithNotExistUserId() {
        return OrderDto.builder()
                .userId(10)
                .certificateId(3)
                .build();
    }

    public static OrderDto orderDtoForMakeOrderWithNotExistCertificateId() {
        return OrderDto.builder()
                .userId(1)
                .certificateId(30)
                .build();
    }

    public static OrderDto orderDtoForMakeOrderWithCost() {
        return OrderDto.builder()
                .userId(1)
                .cost(BigDecimal.valueOf(20.83))
                .certificateId(3)
                .build();
    }

    public static List<Order> orders() {
        return Arrays.asList(orderWithId1(), orderWithId2(), orderWithId3(), orderWithId4());
    }

    public static List<OrderDto> dtoOrders() {
        return Arrays.asList(orderDtoWithId1(), orderDtoWithId2(), orderDtoWithId3(),
                orderDtoWithId4());
    }

    public static Pageable pageable() {
        return PageRequest.of(0, 20);
    }
}
