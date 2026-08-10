package ru.prplhd.tasktracker.backend.dto;

import java.util.List;

public record ValidationErrorResponseDto(List<String> message) {
}
