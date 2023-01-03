package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@Positive @PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserDto> findByNameIgnoreCase(@NotBlank @PathVariable String name) {
        return ResponseEntity.ok(userService.findByNameIgnoreCase(name));
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDto));
    }
}
