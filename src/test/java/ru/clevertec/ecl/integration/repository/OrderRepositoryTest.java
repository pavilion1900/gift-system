package ru.clevertec.ecl.integration.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.integration.IntegrationTestBase;
import ru.clevertec.ecl.repository.OrderRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.clevertec.ecl.testdata.OrderUtil.orderWithId1;
import static ru.clevertec.ecl.testdata.OrderUtil.orderWithId4;
import static ru.clevertec.ecl.testdata.OrderUtil.orders;
import static ru.clevertec.ecl.testdata.OrderUtil.pageable;

@RequiredArgsConstructor
public class OrderRepositoryTest extends IntegrationTestBase {

    private final OrderRepository orderRepository;

    @Test
    void checkFindAll() {
        List<Order> actual = orderRepository.findAll(pageable()).getContent();
        assertEquals(orders(), actual);
    }

    @Test
    void checkFindAllByUserId() {
        List<Order> actual = orderRepository.findAllByUserId(pageable(), 1);
        List<Order> expected = Arrays.asList(orderWithId1(), orderWithId4());
        assertEquals(expected, actual);
    }

    @Test
    void checkFindByIdIfOrderIdExist() {
        Optional<Order> optional = orderRepository.findById(1);
        optional.ifPresent(actual -> assertEquals(orderWithId1(), actual));
    }
}
