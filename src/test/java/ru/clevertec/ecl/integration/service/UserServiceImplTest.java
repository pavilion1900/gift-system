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
import static ru.clevertec.ecl.util.UserUtil.dtoUsers;
import static ru.clevertec.ecl.util.UserUtil.userDtoWithId1;

@RequiredArgsConstructor
public class UserServiceImplTest extends IntegrationTestBase {

    private final UserService service;

    @Test
    void checkFindAll() {
        List<UserDto> actual = service.findAll(PageRequest.of(0, 20));
        assertEquals(dtoUsers(), actual);
    }

    @Test
    void checkFindByIdIfUserIdExist() {
        UserDto actual = service.findById(1);
        assertEquals(userDtoWithId1(), actual);
    }

    @Test
    void throwExceptionIfUserIdNotExist() {
        assertThrows(EntityNotFoundException.class, () -> service.findById(10));
    }

    @Test
    void checkFindByNameIfUserNameExist() {
        UserDto actual = service.findByNameIgnoreCase("Ivanov");
        assertEquals(userDtoWithId1(), actual);
    }

    @Test
    void throwExceptionIfUserNameNotExist() {
        assertThrows(EntityNotFoundException.class,
                () -> service.findByNameIgnoreCase("Ivanov333"));
    }
}
