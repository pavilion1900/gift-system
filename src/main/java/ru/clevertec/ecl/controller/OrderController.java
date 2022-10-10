package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.service.OrderService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(orderService.findAll(pageable));
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<OrderDto>> findAllByUserId(Pageable pageable,
                                                          @Positive @PathVariable Integer userId) {
        return ResponseEntity.ok(orderService.findAllByUserId(pageable, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@Positive @PathVariable Integer id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PostMapping
    public ResponseEntity<OrderDto> makeOrderByCertificateId(
            @Valid @RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.makeOrder(orderDto));
    }
}
