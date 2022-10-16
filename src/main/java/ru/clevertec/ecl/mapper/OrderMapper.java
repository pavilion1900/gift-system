package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.entity.Order;

import java.time.LocalDateTime;

@Mapper(uses = {CertificateMapper.class, TagMapper.class, UserMapper.class},
        imports = LocalDateTime.class)
public interface OrderMapper {

    @Mapping(source = "order.certificate.id", target = "certificateId")
    @Mapping(source = "order.user.id", target = "userId")
    OrderDto toDto(Order order);

    @Mapping(source = "orderDto.id", target = "id")
    @Mapping(target = "purchaseDate", expression = "java(LocalDateTime.now())")
    @Mapping(source = "sourceCertificateDto", target = "certificate")
    @Mapping(source = "sourceUserDto", target = "user")
    Order toEntity(OrderDto orderDto, CertificateDto sourceCertificateDto, UserDto sourceUserDto);
}
