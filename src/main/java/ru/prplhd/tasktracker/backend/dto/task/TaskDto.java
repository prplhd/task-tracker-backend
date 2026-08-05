package ru.prplhd.tasktracker.backend.dto.task;

import java.time.Instant;

public record TaskDto(Long id, String title, String description, boolean completed, Instant completedAt) {
}
