package ru.prplhd.tasktracker.backend.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import ru.prplhd.tasktracker.backend.event.UserRegisteredEvent;
import ru.prplhd.tasktracker.backend.message.EmailSendingTask;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRegisteredEventListener {

    private static final String EMAIL_SENDING_TOPIC_NAME = "EMAIL_SENDING_TASKS";

    private final KafkaTemplate<String, EmailSendingTask> kafkaTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(UserRegisteredEvent userRegisteredEvent) {
        EmailSendingTask emailSendingTask = new EmailSendingTask(
                userRegisteredEvent.email(),
                "Welcome to Task Tracker",
                "Thanks for registration! Your account has been successfully created."
        );

        CompletableFuture<SendResult<String, EmailSendingTask>> future = kafkaTemplate.send(EMAIL_SENDING_TOPIC_NAME, emailSendingTask);

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Failed to send welcome email task for {}", userRegisteredEvent.email(), exception);
            } else {
                log.info("Message for {} sent successfully: {}", userRegisteredEvent.email(), result.getRecordMetadata());
            }
        });
    }
}
