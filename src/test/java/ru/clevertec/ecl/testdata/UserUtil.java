package ru.clevertec.ecl.testdata;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.entity.User;

import java.util.Arrays;
import java.util.List;

public class UserUtil {

    public static User userWithId1() {
        return User.builder()
                .id(1)
                .name("Ivanov")
                .build();
    }

    public static User userWithId2() {
        return User.builder()
                .id(2)
                .name("Petrov")
                .build();
    }

    public static User userWithId3() {
        return User.builder()
                .id(3)
                .name("Sidorov")
                .build();
    }

    public static UserDto userDtoWithId1() {
        return UserDto.builder()
                .id(1)
                .name("Ivanov")
                .build();
    }

    public static UserDto userDtoWithId2() {
        return UserDto.builder()
                .id(2)
                .name("Petrov")
                .build();
    }

    public static UserDto userDtoWithId3() {
        return UserDto.builder()
                .id(3)
                .name("Sidorov")
                .build();
    }

    public static List<User> users() {
        return Arrays.asList(userWithId1(), userWithId2(), userWithId3());
    }

    public static List<UserDto> dtoUsers() {
        return Arrays.asList(userDtoWithId1(), userDtoWithId2(), userDtoWithId3());
    }

    public static Pageable pageable() {
        return PageRequest.of(0, 20);
    }
}
