package ru.prplhd.tasktracker.backend.dto.auth;

import ru.prplhd.tasktracker.backend.dto.UserDto;

public record RegistrationResult(UserDto userDto, String accessToken) {
}
