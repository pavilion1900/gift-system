package ru.clevertec.ecl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.mapper.OrderMapper;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.service.impl.OrderServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.clevertec.ecl.util.CertificateUtil.certificateDtoWithId3;
import static ru.clevertec.ecl.util.OrderUtil.dtoOrders;
import static ru.clevertec.ecl.util.OrderUtil.orderDtoForMakeOrder;
import static ru.clevertec.ecl.util.OrderUtil.orderDtoForMakeOrderWithCost;
import static ru.clevertec.ecl.util.OrderUtil.orderDtoWithId1;
import static ru.clevertec.ecl.util.OrderUtil.orderDtoWithId2;
import static ru.clevertec.ecl.util.OrderUtil.orderDtoWithId3;
import static ru.clevertec.ecl.util.OrderUtil.orderDtoWithId4;
import static ru.clevertec.ecl.util.OrderUtil.orderWithId1;
import static ru.clevertec.ecl.util.OrderUtil.orderWithId2;
import static ru.clevertec.ecl.util.OrderUtil.orderWithId3;
import static ru.clevertec.ecl.util.OrderUtil.orderWithId4;
import static ru.clevertec.ecl.util.OrderUtil.orderWithoutId;
import static ru.clevertec.ecl.util.OrderUtil.orders;
import static ru.clevertec.ecl.util.OrderUtil.pageable;
import static ru.clevertec.ecl.util.UserUtil.userDtoWithId1;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CertificateService certificateService;

    @Mock
    private UserService userService;
    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl service;

    @Test
    void checkFindAll() {
        doReturn(new PageImpl<>(orders()))
                .when(orderRepository).findAll(pageable());
        doReturn(orderDtoWithId1())
                .when(orderMapper).toDto(orderWithId1());
        doReturn(orderDtoWithId2())
                .when(orderMapper).toDto(orderWithId2());
        doReturn(orderDtoWithId3())
                .when(orderMapper).toDto(orderWithId3());
        doReturn(orderDtoWithId4())
                .when(orderMapper).toDto(orderWithId4());
        List<OrderDto> actual = service.findAll(pageable());
        assertEquals(dtoOrders(), actual);
        verify(orderRepository).findAll(pageable());
        verify(orderMapper, times(4)).toDto(any(Order.class));
    }

    @Test
    void checkFindAllByUserId() {
        doReturn(Arrays.asList(orderWithId1(), orderWithId4()))
                .when(orderRepository).findAllByUserId(pageable(), 1);
        doReturn(orderDtoWithId1())
                .when(orderMapper).toDto(orderWithId1());
        doReturn(orderDtoWithId4())
                .when(orderMapper).toDto(orderWithId4());
        List<OrderDto> actual = service.findAllByUserId(pageable(), 1);
        List<OrderDto> expected = Arrays.asList(orderDtoWithId1(), orderDtoWithId4());
        assertEquals(expected, actual);
        verify(orderRepository).findAllByUserId(pageable(), 1);
        verify(orderMapper, times(2)).toDto(any(Order.class));
    }

    @Test
    void checkFindByIdIfOrderIdExist() {
        doReturn(Optional.of(orderWithId1()))
                .when(orderRepository).findById(1);
        doReturn(orderDtoWithId1())
                .when(orderMapper).toDto(orderWithId1());
        OrderDto actual = service.findById(1);
        assertEquals(orderDtoWithId1(), actual);
        verify(orderRepository).findById(1);
        verify(orderMapper).toDto(orderWithId1());
    }

    @Test
    void throwExceptionIfOrderIdNotExist() {
        doReturn(Optional.empty())
                .when(orderRepository).findById(1);
        assertThrows(EntityNotFoundException.class, () -> service.findById(1));
    }

    @Test
    void checkMakeOrderIfCertificateIdAndOrderIdExist() {
        doReturn(certificateDtoWithId3())
                .when(certificateService).findById(orderDtoForMakeOrder().getCertificateId());
        doReturn(userDtoWithId1())
                .when(userService).findById(orderDtoForMakeOrder().getUserId());
        doReturn(orderWithoutId())
                .when(orderMapper).toEntity(
                        orderDtoForMakeOrderWithCost(), certificateDtoWithId3(), userDtoWithId1());
        doReturn(orderWithId1())
                .when(orderRepository).save(orderWithoutId());
        doReturn(orderDtoWithId1())
                .when(orderMapper).toDto(orderWithId1());
        OrderDto actual = service.makeOrder(orderDtoForMakeOrder());
        assertEquals(orderDtoWithId1(), actual);
        verify(orderRepository).save(orderWithoutId());
        verify(orderMapper).toDto(orderWithId1());
    }

    @Test
    void throwExceptionWhenMakeOrderIfCertificateIdNotExist() {
        doThrow(EntityNotFoundException.class)
                .when(certificateService).findById(orderDtoForMakeOrder().getCertificateId());
        assertThrows(EntityNotFoundException.class,
                () -> service.makeOrder(orderDtoForMakeOrder()));
    }

    @Test
    void throwExceptionWhenMakeOrderIfUserIdNotExist() {
        doReturn(certificateDtoWithId3())
                .when(certificateService).findById(orderDtoForMakeOrder().getCertificateId());
        doThrow(EntityNotFoundException.class)
                .when(userService).findById(orderDtoForMakeOrder().getUserId());
        assertThrows(EntityNotFoundException.class,
                () -> service.makeOrder(orderDtoForMakeOrder()));
    }
}
