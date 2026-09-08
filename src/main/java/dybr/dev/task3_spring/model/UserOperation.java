package dybr.dev.task3_spring.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserOperation {

    USER_DELETION("Здравствуйте! Ваш аккаунт был удалён."),
    USER_CREATION("Здравствуйте! Ваш аккаунт на сайте был успешно создан.");

    private final String message;
}