package ru.prplhd.tasktracker.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prplhd.tasktracker.backend.dto.UserDto;
import ru.prplhd.tasktracker.backend.entity.UserEntity;
import ru.prplhd.tasktracker.backend.exception.UserAlreadyExistsException;
import ru.prplhd.tasktracker.backend.exception.UserNotFoundException;
import ru.prplhd.tasktracker.backend.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto register(String email, String password) {

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new UserAlreadyExistsException("This user already exists");
        }

        String passwordHash = passwordEncoder.encode(password);
        UserEntity userEntity = new UserEntity(email, passwordHash);

        UserEntity savedUser = userRepository.saveAndFlush(userEntity);

        return new UserDto(savedUser.getId(), savedUser.getEmail());
    }

    public UserDto getById(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id '%d' not found".formatted(userId)));

        return new UserDto(user.getId(), user.getEmail());
    }
}
