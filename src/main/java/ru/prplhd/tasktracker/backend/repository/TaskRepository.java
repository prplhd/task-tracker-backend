package ru.prplhd.tasktracker.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.prplhd.tasktracker.backend.entity.TaskEntity;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<TaskEntity> findByIdAndOwner_Id(Long taskId, Long ownerId);

    List<TaskEntity> findAllByOwnerIdOrderByIdDesc(Long ownerId);
}
