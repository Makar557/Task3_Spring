package dybr.dev.task3_spring.model;

import org.springframework.stereotype.Component;

public record UserNotification(
        Long userID,
        String email,
        OperationsOnUser operation
) {
}
