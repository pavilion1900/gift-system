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
import static ru.clevertec.ecl.util.OrderUtil.dtoOrders;
import static ru.clevertec.ecl.util.OrderUtil.orderDtoForMakeOrder;
import static ru.clevertec.ecl.util.OrderUtil.orderDtoForMakeOrderWithNotExistCertificateId;
import static ru.clevertec.ecl.util.OrderUtil.orderDtoForMakeOrderWithNotExistUserId;
import static ru.clevertec.ecl.util.OrderUtil.orderDtoWithId1;
import static ru.clevertec.ecl.util.OrderUtil.orderDtoWithId4;
import static ru.clevertec.ecl.util.OrderUtil.pageable;

@RequiredArgsConstructor
public class OrderServiceImplTest extends IntegrationTestBase {

    private final OrderService service;

    @Test
    void checkFindAll() {
        List<OrderDto> actual = service.findAll(pageable());
        assertEquals(dtoOrders(), actual);
    }

    @Test
    void checkFindAllByUserId() {
        List<OrderDto> actual = service.findAllByUserId(pageable(), 1);
        List<OrderDto> expected = Arrays.asList(orderDtoWithId1(), orderDtoWithId4());
        assertEquals(expected, actual);
    }

    @Test
    void checkFindByIdIfOrderIdExist() {
        OrderDto actual = service.findById(1);
        assertEquals(orderDtoWithId1(), actual);
    }

    @Test
    void throwExceptionIfOrderIdNotExist() {
        assertThrows(EntityNotFoundException.class, () -> service.findById(10));
    }

    @Test
    void checkMakeOrderIfCertificateIdAndOrderIdExist() {
        OrderDto actual = service.makeOrder(orderDtoForMakeOrder());
        assertEquals(orderDtoWithId1(), actual);
    }

    @Test
    void throwExceptionWhenMakeOrderIfCertificateIdNotExist() {
        assertThrows(EntityNotFoundException.class,
                () -> service.makeOrder(orderDtoForMakeOrderWithNotExistUserId()));
    }

    @Test
    void throwExceptionWhenMakeOrderIfUserIdNotExist() {
        assertThrows(EntityNotFoundException.class,
                () -> service.makeOrder(orderDtoForMakeOrderWithNotExistCertificateId()));
    }
}
