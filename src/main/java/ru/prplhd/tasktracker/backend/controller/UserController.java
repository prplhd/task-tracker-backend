package ru.prplhd.tasktracker.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.prplhd.tasktracker.backend.dto.UserDto;
import ru.prplhd.tasktracker.backend.dto.auth.RegistrationRequestDto;
import ru.prplhd.tasktracker.backend.dto.auth.RegistrationResult;
import ru.prplhd.tasktracker.backend.service.AuthService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid RegistrationRequestDto registrationRequestDto) {
        String email = registrationRequestDto.email();
        String password = registrationRequestDto.password();

        RegistrationResult registrationResult = authService.signUp(email, password);

        return ResponseEntity.ok()
                .headers(headers -> headers.setBearerAuth(registrationResult.accessToken()))
                .body(registrationResult.userDto());
    }
}
