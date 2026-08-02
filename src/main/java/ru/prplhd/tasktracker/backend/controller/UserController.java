package ru.prplhd.tasktracker.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.prplhd.tasktracker.backend.dto.RegistrationRequestDto;
import ru.prplhd.tasktracker.backend.dto.UserDto;
import ru.prplhd.tasktracker.backend.dto.UserResponseDto;
import ru.prplhd.tasktracker.backend.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody @Valid RegistrationRequestDto registrationRequestDto) {
        String email = registrationRequestDto.email();
        String password = registrationRequestDto.password();

        UserDto registeredUser = userService.register(email, password);
    }
}
