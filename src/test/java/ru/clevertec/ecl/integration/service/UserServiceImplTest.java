package ru.clevertec.ecl.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.integration.IntegrationTestBase;
import ru.clevertec.ecl.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.clevertec.ecl.testdata.UserUtil.dtoUsers;
import static ru.clevertec.ecl.testdata.UserUtil.userDtoWithId1;

@RequiredArgsConstructor
public class UserServiceImplTest extends IntegrationTestBase {

    private final UserService userService;

    @Test
    void checkFindAll() {
        List<UserDto> actual = userService.findAll(PageRequest.of(0, 20));
        assertEquals(dtoUsers(), actual);
    }

    @Test
    void checkFindByIdIfUserIdExist() {
        UserDto actual = userService.findById(1);
        assertEquals(userDtoWithId1(), actual);
    }

    @Test
    void throwExceptionIfUserIdNotExist() {
        assertThrows(EntityNotFoundException.class, () -> userService.findById(10));
    }

    @Test
    void checkFindByNameIfUserNameExist() {
        UserDto actual = userService.findByNameIgnoreCase("Ivanov");
        assertEquals(userDtoWithId1(), actual);
    }

    @Test
    void throwExceptionIfUserNameNotExist() {
        assertThrows(EntityNotFoundException.class, () -> userService.findByNameIgnoreCase("Ivanov333"));
    }
}
