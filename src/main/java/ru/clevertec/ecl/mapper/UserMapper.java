package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.entity.User;

@Mapper(uses = OrderMapper.class)
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "orders", ignore = true)
    User toEntity(UserDto userDto);
}
