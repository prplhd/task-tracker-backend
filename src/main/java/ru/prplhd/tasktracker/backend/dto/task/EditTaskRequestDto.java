package ru.prplhd.tasktracker.backend.dto.task;

import jakarta.validation.constraints.Size;

public record EditTaskRequestDto(

        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        Boolean completed
) {
}