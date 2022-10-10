package ru.clevertec.ecl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.entity.User;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.mapper.UserMapper;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.clevertec.ecl.util.UserUtil.dtoUsers;
import static ru.clevertec.ecl.util.UserUtil.pageable;
import static ru.clevertec.ecl.util.UserUtil.userDtoWithId1;
import static ru.clevertec.ecl.util.UserUtil.userDtoWithId2;
import static ru.clevertec.ecl.util.UserUtil.userDtoWithId3;
import static ru.clevertec.ecl.util.UserUtil.userWithId1;
import static ru.clevertec.ecl.util.UserUtil.userWithId2;
import static ru.clevertec.ecl.util.UserUtil.userWithId3;
import static ru.clevertec.ecl.util.UserUtil.users;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void checkFindAll() {
        doReturn(new PageImpl<>(users()))
                .when(repository).findAll(pageable());
        doReturn(userDtoWithId1())
                .when(userMapper).toDto(userWithId1());
        doReturn(userDtoWithId2())
                .when(userMapper).toDto(userWithId2());
        doReturn(userDtoWithId3())
                .when(userMapper).toDto(userWithId3());
        List<UserDto> actual = service.findAll(pageable());
        assertEquals(dtoUsers(), actual);
        verify(repository).findAll(pageable());
        verify(userMapper, times(3)).toDto(any(User.class));
    }

    @Test
    void checkFindByIdIfUserIdExist() {
        doReturn(Optional.of(userWithId1()))
                .when(repository).findById(1);
        doReturn(userDtoWithId1())
                .when(userMapper).toDto(userWithId1());
        UserDto actual = service.findById(1);
        assertEquals(userDtoWithId1(), actual);
        verify(repository).findById(1);
        verify(userMapper).toDto(userWithId1());
    }

    @Test
    void throwExceptionIfUserIdNotExist() {
        doReturn(Optional.empty())
                .when(repository).findById(1);
        assertThrows(EntityNotFoundException.class, () -> service.findById(1));
    }

    @Test
    void checkFindByNameIfUserNameExist() {
        doReturn(Optional.of(userWithId1()))
                .when(repository).findByNameIgnoreCase("Ivanov");
        doReturn(userDtoWithId1())
                .when(userMapper).toDto(userWithId1());
        UserDto actual = service.findByNameIgnoreCase("Ivanov");
        assertEquals(userDtoWithId1(), actual);
        verify(repository).findByNameIgnoreCase("Ivanov");
        verify(userMapper).toDto(userWithId1());
    }

    @Test
    void throwExceptionIfUserNameNotExist() {
        doReturn(Optional.empty())
                .when(repository).findByNameIgnoreCase("Ivanov");
        assertThrows(EntityNotFoundException.class, () -> service.findByNameIgnoreCase("Ivanov"));
    }
}
