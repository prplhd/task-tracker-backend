package ru.prplhd.tasktracker.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.prplhd.tasktracker.backend.entity.TaskEntity;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    public List<TaskEntity> findAllByOwnerId(Long userId);
}
