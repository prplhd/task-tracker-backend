package ru.prplhd.tasktracker.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prplhd.tasktracker.backend.dto.UserDto;
import ru.prplhd.tasktracker.backend.entity.UserEntity;
import ru.prplhd.tasktracker.backend.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto register(String email, String password) {
        String passwordHash = passwordEncoder.encode(password);

        UserEntity userEntity = new UserEntity(email, passwordHash);

        UserEntity savedUser = userRepository.save(userEntity);

        return new UserDto(savedUser.getId(), savedUser.getEmail());
    }
}
