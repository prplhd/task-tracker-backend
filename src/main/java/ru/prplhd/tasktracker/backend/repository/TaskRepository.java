package ru.prplhd.tasktracker.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.prplhd.tasktracker.backend.entity.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
