package dybr.dev.task3_spring.controller;

import dybr.dev.task3_spring.dto.UserCreateDTO;
import dybr.dev.task3_spring.dto.UserResponseDTO;
import dybr.dev.task3_spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import org.slf4j.Logger;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping()
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserCreateDTO user) {

        logger.info("Создание пользователя: {}", user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable("id") Long id) {

        logger.info("Получение пользователя с id={}", id);

        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> findAll() {

        logger.info("Получение списка пользователей");

        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDTO> deleteById(@PathVariable Long id) {

        logger.info("Удаление пользователя с id={}", id);

        return ResponseEntity.ok(userService.deleteById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@RequestBody @Valid UserCreateDTO user, @PathVariable Long id) {

        logger.info("Обновление пользователя с id={}, данные={}", id, user);

        return ResponseEntity.ok(userService.update(user, id));
    }
}