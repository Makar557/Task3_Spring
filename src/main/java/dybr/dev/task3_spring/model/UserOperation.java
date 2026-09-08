package dybr.dev.task3_spring.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserOperation {

    USER_DELETION("Здравствуйте! Ваш аккаунт был удалён."),
    USER_CREATION("Здравствуйте! Ваш аккаунт на сайте был успешно создан.");

    private final String message;

    public String getMessage() {
        return message;
    }
}