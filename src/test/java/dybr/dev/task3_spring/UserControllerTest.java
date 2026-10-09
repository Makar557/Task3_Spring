package dybr.dev.task3_spring;

import dybr.dev.task3_spring.controller.UserController;
import dybr.dev.task3_spring.dto.UserCreateDTO;
import dybr.dev.task3_spring.dto.UserResponseDTO;
import dybr.dev.task3_spring.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void findById_shouldReturnUser() throws Exception {

        UserResponseDTO user = new UserResponseDTO(
                1L,
                "Makar",
                "makar@gmail.com",
                23
        );

        when(userService.findById(1L))
                .thenReturn(user);

        mockMvc.perform(
                get("/api/users/1")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Makar"))
                .andExpect(jsonPath("$.email").value("makar@gmail.com"))
                .andExpect(jsonPath("$.age").value(23));

        verify(userService).findById(1L);
    }

    @Test
    void findAll_shouldReturnUsers() throws Exception {

        List<UserResponseDTO> users = List.of(
                new UserResponseDTO(
                        1L,
                        "Makar",
                        "makar@gmail.com",
                        23
                ),
                new UserResponseDTO(
                        2L,
                        "Ivan",
                        "ivan@gmail.com",
                        55
                )
        );

        when(userService.findAll())
                .thenReturn(users);

        mockMvc.perform(
                        get("/api/users")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.userResponseDTOList", hasSize(2)))
                .andExpect(jsonPath("_embedded.userResponseDTOList[0].name").value("Makar"))
                .andExpect(jsonPath("_embedded.userResponseDTOList[1].name").value("Ivan"));

        verify(userService).findAll();
    }

    @Test
    void createUser_shouldReturnCreatedUser() throws Exception {

        UserResponseDTO userResponse = new UserResponseDTO(
                1L,
                "Makar",
                "makar@gmail.com",
                23
        );

        UserCreateDTO user = new UserCreateDTO(
                "Makar",
                "makar@gmail.com",
                23
        );

        String jsonUser = objectMapper.writeValueAsString(user);

        when(userService.createUser(user)
        ).thenReturn(userResponse);


        mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser)
        )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(userResponse)));

        verify(userService).createUser(user);
    }

    @Test
    void deleteById_shouldReturnDeletedUser() throws Exception {

        UserResponseDTO userResponse = new UserResponseDTO(
                1L,
                "Makar",
                "makar@gmail.com",
                23
        );

        when(userService.deleteById(1L))
                .thenReturn(userResponse);

        mockMvc.perform(
                        delete("/api/users/1")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userResponse)));

        verify(userService).deleteById(1L);
    }

    @Test
    void update_shouldReturnUpdatedUser() throws Exception {

        UserCreateDTO user = new UserCreateDTO(
                "Makar",
                "makar@gmail.com",
                23
        );

        UserResponseDTO userAfterUpdate = new UserResponseDTO(
                1L,
                "Makar",
                "makar@gmail.com",
                23
        );

        when(userService.update(user, 1L))
                .thenReturn(userAfterUpdate);

        String jsonUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(
                put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser)
        )
                .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(userAfterUpdate)));

        verify(userService).update(user, 1L);
    }

    @Test
    void findById_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {

        when(userService.findById(1L))
                .thenThrow(new EntityNotFoundException());

        mockMvc.perform(
                        get("/api/users/1")
                )
                .andExpect(status().isNotFound())

                .andExpect(jsonPath("$.message").value("Пользователь не найден"))
                .andExpect(jsonPath("$.errorTime").exists());
    }
    @Test
    void findById_shouldReturnBadRequest_whenIdIsInvalid() throws Exception {

        mockMvc.perform(
                        get("/api/users/abc")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Некорректный аргумент"))
                .andExpect(jsonPath("$.detailedMessage").value("Параметр ID должен иметь тип Long"))
                .andExpect(jsonPath("$.errorTime").exists());

        verifyNoInteractions(userService);
    }
    @Test
    void findById_shouldReturnBadRequest_whenIllegalStateExceptionOccurs() throws Exception {

        when(userService.findById(1L))
                .thenThrow(new IllegalStateException("Некорректное состояние"));

        mockMvc.perform(
                        get("/api/users/1")
                )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Внутренняя ошибка сервера"))
                .andExpect(jsonPath("$.detailedMessage").doesNotExist())
                .andExpect(jsonPath("$.errorTime").exists());
    }


    @Test
    void createUser_shouldReturnBadRequest_whenValidationFails() throws Exception {

        String invalidJson = """
        {
            "name": "",
            "email": "qwerty",
            "age": 0
        }
        """;

        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidJson)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Некорректный запрос"))
                .andExpect(jsonPath("$.detailedMessage").exists())
                .andExpect(jsonPath("$.errorTime").exists());
    }


    @Test
    void findById_shouldReturnInternalServerError_whenUnexpectedExceptionOccurs() throws Exception {

        when(userService.findById(1L))
                .thenThrow(new RuntimeException("Ошибка сервера"));

        mockMvc.perform(
                        get("/api/users/1")
                )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Внутренняя ошибка сервера"))
                .andExpect(jsonPath("$.detailedMessage").doesNotExist())
                .andExpect(jsonPath("$.errorTime").exists());
    }
}