package ru.prplhd.tasktracker.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.prplhd.tasktracker.backend.dto.UserDto;
import ru.prplhd.tasktracker.backend.dto.auth.RegistrationRequestDto;
import ru.prplhd.tasktracker.backend.dto.auth.RegistrationResult;
import ru.prplhd.tasktracker.backend.service.AuthService;
import ru.prplhd.tasktracker.backend.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
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

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());

        UserDto userDto = userService.getById(userId);

        return ResponseEntity.ok()
                .body(userDto);
    }
}
