package dybr.dev.task3_spring.model;

public record UserNotification(
        Long userId,
        String email,
        UserOperation operation
) {
}
