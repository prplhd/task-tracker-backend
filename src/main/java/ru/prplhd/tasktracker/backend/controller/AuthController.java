package ru.prplhd.tasktracker.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.prplhd.tasktracker.backend.dto.UserDto;
import ru.prplhd.tasktracker.backend.dto.auth.SignInRequestDto;
import ru.prplhd.tasktracker.backend.dto.auth.SignInResult;
import ru.prplhd.tasktracker.backend.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> signIn(@RequestBody @Valid SignInRequestDto signInRequestDto) {
        String email = signInRequestDto.email();
        String password = signInRequestDto.password();

        SignInResult signInResult = authService.signIn(email, password);

        return ResponseEntity.ok()
                .headers(headers -> headers.setBearerAuth(signInResult.accessToken()))
                .body(signInResult.userDto());
    }
}
