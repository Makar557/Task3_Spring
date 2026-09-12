package dybr.dev.task3_spring.kafka;

import dybr.dev.task3_spring.model.UserNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserKafkaProducer {

    private final KafkaTemplate<Long, UserNotification> kafkaTemplate;

    public void sendMessage(UserNotification notification) {
        kafkaTemplate.send("users", notification.userId(), notification);
    }
}
