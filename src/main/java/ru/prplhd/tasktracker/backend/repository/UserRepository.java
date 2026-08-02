package ru.prplhd.tasktracker.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.prplhd.tasktracker.backend.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
