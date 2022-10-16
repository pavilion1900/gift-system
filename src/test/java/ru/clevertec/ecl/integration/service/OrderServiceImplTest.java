package ru.clevertec.ecl.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.integration.IntegrationTestBase;
import ru.clevertec.ecl.service.OrderService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.clevertec.ecl.testdata.OrderUtil.dtoOrders;
import static ru.clevertec.ecl.testdata.OrderUtil.orderDtoForMakeOrder;
import static ru.clevertec.ecl.testdata.OrderUtil.orderDtoForMakeOrderWithNotExistCertificateId;
import static ru.clevertec.ecl.testdata.OrderUtil.orderDtoForMakeOrderWithNotExistUserId;
import static ru.clevertec.ecl.testdata.OrderUtil.orderDtoWithId1;
import static ru.clevertec.ecl.testdata.OrderUtil.orderDtoWithId4;
import static ru.clevertec.ecl.testdata.OrderUtil.pageable;

@RequiredArgsConstructor
public class OrderServiceImplTest extends IntegrationTestBase {

    private final OrderService orderService;

    @Test
    void checkFindAll() {
        List<OrderDto> actual = orderService.findAll(pageable());
        assertEquals(dtoOrders(), actual);
    }

    @Test
    void checkFindAllByUserId() {
        List<OrderDto> actual = orderService.findAllByUserId(pageable(), 1);
        List<OrderDto> expected = Arrays.asList(orderDtoWithId1(), orderDtoWithId4());
        assertEquals(expected, actual);
    }

    @Test
    void checkFindByIdIfOrderIdExist() {
        OrderDto actual = orderService.findById(1);
        assertEquals(orderDtoWithId1(), actual);
    }

    @Test
    void throwExceptionIfOrderIdNotExist() {
        assertThrows(EntityNotFoundException.class, () -> orderService.findById(10));
    }

    @Test
    void checkMakeOrderIfCertificateIdAndOrderIdExist() {
        OrderDto actual = orderService.makeOrder(orderDtoForMakeOrder());
        assertEquals(orderDtoWithId1(), actual);
    }

    @Test
    void throwExceptionWhenMakeOrderIfCertificateIdNotExist() {
        assertThrows(EntityNotFoundException.class,
                () -> orderService.makeOrder(orderDtoForMakeOrderWithNotExistUserId()));
    }

    @Test
    void throwExceptionWhenMakeOrderIfUserIdNotExist() {
        assertThrows(EntityNotFoundException.class,
                () -> orderService.makeOrder(orderDtoForMakeOrderWithNotExistCertificateId()));
    }
}
