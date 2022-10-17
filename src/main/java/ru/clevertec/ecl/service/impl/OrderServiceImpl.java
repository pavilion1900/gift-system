package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.mapper.OrderMapper;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.service.CertificateService;
import ru.clevertec.ecl.service.OrderService;
import ru.clevertec.ecl.service.UserService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final CertificateService certificateService;
    private final UserService userService;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).stream()
                .map(orderMapper::toDto)
                .collect(toList());
    }

    @Override
    public List<OrderDto> findAllByUserId(Pageable pageable, Integer userId) {
        return orderRepository.findAllByUserId(pageable, userId).stream()
                .map(orderMapper::toDto)
                .collect(toList());
    }

    @Override
    public OrderDto findById(Integer id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id %d not found", id)));
    }

    @Override
    public Integer findLastSequenceValue() {
        return orderRepository.findLastSequenceValue();
    }

    @Override
    @Transactional
    public Integer setSequenceValue(Integer sequenceValue) {
        return orderRepository.setSequenceValue(sequenceValue);
    }

    @Override
    @Transactional
    public OrderDto makeOrder(OrderDto orderDto) {
        Order order = buildOrder(orderDto);
        return orderMapper.toDto(orderRepository.save(order));
    }

    private Order buildOrder(OrderDto orderDto) {
        CertificateDto certificateDto = certificateService.findById(orderDto.getCertificateId());
        UserDto userDto = userService.findById(orderDto.getUserId());
        orderDto.setCost(certificateDto.getPrice());
        return orderMapper.toEntity(orderDto, certificateDto, userDto);
    }
}
