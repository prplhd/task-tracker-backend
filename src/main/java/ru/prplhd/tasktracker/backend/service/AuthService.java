package ru.prplhd.tasktracker.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prplhd.tasktracker.backend.dto.UserDto;
import ru.prplhd.tasktracker.backend.dto.auth.RegistrationResult;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;

    @Transactional
    public RegistrationResult signUp(String email, String password) {
        UserDto userDto = userService.register(email, password);

        String accessToken = jwtService.generateAccessToken(userDto.id());

        return new RegistrationResult(userDto, accessToken);
    }
}
