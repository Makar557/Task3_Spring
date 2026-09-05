package dybr.dev.task3_spring.kafka;

import dybr.dev.task3_spring.model.UserNotification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserKafkaProducer {

    private final KafkaTemplate<Long, UserNotification> kafkaTemplate;

    public UserKafkaProducer(KafkaTemplate<Long, UserNotification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(UserNotification notification) {
        kafkaTemplate.send("users", notification.userID(), notification);
    }
}
