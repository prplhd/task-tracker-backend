package ru.prplhd.tasktracker.backend.message;

public record EmailSendingTask(
        String recipient,
        String subject,
        String text
) {
}