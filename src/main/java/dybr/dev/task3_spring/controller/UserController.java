package dybr.dev.task3_spring.controller;

import dybr.dev.task3_spring.annotation.exception.InvalidIdResponse;
import dybr.dev.task3_spring.annotation.exception.InvalidJsonResponse;
import dybr.dev.task3_spring.annotation.exception.UserNotFoundResponse;
import dybr.dev.task3_spring.annotation.exception.ValidationErrorResponse;
import dybr.dev.task3_spring.annotation.success.UserCreatedResponse;
import dybr.dev.task3_spring.annotation.success.UserDeletedResponse;
import dybr.dev.task3_spring.annotation.success.UserFoundResponse;
import dybr.dev.task3_spring.annotation.success.UserUpdatedResponse;
import dybr.dev.task3_spring.annotation.success.UsersListResponse;
import dybr.dev.task3_spring.dto.UserCreateDTO;
import dybr.dev.task3_spring.dto.UserResponseDTO;
import dybr.dev.task3_spring.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

@Tag(name = "Users", description = "Методы работы с пользователями")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @UserCreatedResponse
    @ValidationErrorResponse
    @InvalidJsonResponse
    @Operation(summary = "Создание пользователя", description = "Создает пользователя, если данные проходят валидацию", operationId = "createUser")

    @PostMapping()
    public ResponseEntity<EntityModel<UserResponseDTO>> createUser(@RequestBody @Valid UserCreateDTO userInput) {

        logger.info("Создание пользователя: {}", userInput);

        UserResponseDTO user = userService.createUser(userInput);

        EntityModel<UserResponseDTO> model = UserOperation.CREATE.generate(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @Operation(summary = "Получение пользователя по id", description = "Возвращает пользователя, если он найден", operationId = "findById")
    @Parameter(name = "id", description = "Идентификатор записи в базе данных в формате Long", example = "1", required = true)
    @UserFoundResponse
    @UserNotFoundResponse
    @InvalidIdResponse

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDTO>> findById(@PathVariable("id") Long id) {

        logger.info("Получение пользователя с id={}", id);

        UserResponseDTO user = userService.findById(id);

        EntityModel<UserResponseDTO> model = UserOperation.FIND_BY_ID.generate(user);
        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Получение списка пользователей", description = "Возвращает список пользователей, может вернуть пустой список, если пользователей нет", operationId = "findAll")
    @UsersListResponse

    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<UserResponseDTO>>> findAll() {

        logger.info("Получение списка пользователей");

        List<UserResponseDTO> users = userService.findAll();

        CollectionModel<EntityModel<UserResponseDTO>> model = UserOperation.FIND_ALL.generate(users);

        return ResponseEntity.ok(model);
    }


    @Operation(summary = "Удаление пользователя по id", description = "Удаляет пользователя по id и возвращает его", operationId = "deleteById")
    @Parameter(name = "id", description = "Идентификатор записи в базе данных в формате Long", example = "1", required = true)
    @UserDeletedResponse
    @UserNotFoundResponse
    @InvalidIdResponse

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDTO>> deleteById(@PathVariable Long id) {

        logger.info("Удаление пользователя с id={}", id);

        UserResponseDTO user = userService.deleteById(id);

        EntityModel<UserResponseDTO> model = UserOperation.DELETE.generate(user);

        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Обновление пользователя по id", description = "Обновляет имя, почту и возраст у пользователя целиком", operationId = "update")
    @Parameter(name = "id", description = "Идентификатор записи в базе данных в формате Long", example = "1", required = true)
    @UserUpdatedResponse
    @UserNotFoundResponse
    @InvalidIdResponse
    @ValidationErrorResponse
    @InvalidJsonResponse

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDTO>> update(@RequestBody @Valid UserCreateDTO userInput, @PathVariable Long id) {

        logger.info("Обновление пользователя с id={}, данные={}", id, userInput);

        UserResponseDTO user = userService.update(userInput, id);

        EntityModel<UserResponseDTO> model = UserOperation.UPDATE.generate(user);

        return ResponseEntity.ok(model);
    }
}