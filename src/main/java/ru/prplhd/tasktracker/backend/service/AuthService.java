package ru.prplhd.tasktracker.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prplhd.tasktracker.backend.dto.UserDto;
import ru.prplhd.tasktracker.backend.dto.auth.RegistrationResult;
import ru.prplhd.tasktracker.backend.dto.auth.SignInResult;
import ru.prplhd.tasktracker.backend.entity.UserEntity;
import ru.prplhd.tasktracker.backend.exception.InvalidCredentialsException;
import ru.prplhd.tasktracker.backend.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegistrationResult signUp(String email, String password) {
        UserDto userDto = userService.register(email, password);

        String accessToken = jwtService.generateAccessToken(userDto.id());

        return new RegistrationResult(userDto, accessToken);
    }

    public SignInResult signIn(String email, String password) {
        UserEntity user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (passwordEncoder.matches(password, user.getPasswordHash())) {
            UserDto userDto = new UserDto(user.getId(), user.getEmail());
            String accessToken = jwtService.generateAccessToken(userDto.id());

            return new SignInResult(userDto, accessToken);
        }

        throw new InvalidCredentialsException("Invalid email or password");
    }
}
