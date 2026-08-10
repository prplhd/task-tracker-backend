package ru.prplhd.tasktracker.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prplhd.tasktracker.backend.dto.task.TaskDto;
import ru.prplhd.tasktracker.backend.entity.TaskEntity;
import ru.prplhd.tasktracker.backend.entity.UserEntity;
import ru.prplhd.tasktracker.backend.exception.TaskNotFoundException;
import ru.prplhd.tasktracker.backend.repository.TaskRepository;
import ru.prplhd.tasktracker.backend.repository.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<TaskDto> getTasksByOwnerId(Long ownerId) {
        List<TaskEntity> tasks = taskRepository.findAllByOwnerIdOrderByIdDesc(ownerId);

        List<TaskDto> taskDtos = new ArrayList<>();

        for (TaskEntity task : tasks) {
           taskDtos.add(new TaskDto(
                   task.getId(),
                   task.getTitle(),
                   task.getDescription(),
                   task.isCompleted(),
                   task.getCompletedAt()
           ));
        }

        return taskDtos;
    }

    @Transactional
    public TaskDto createTask(Long ownerId, String title, String description) {
        UserEntity owner = userRepository.getReferenceById(ownerId);

        TaskEntity task = new TaskEntity(owner, title, description);
        TaskEntity savedTask = taskRepository.save(task);

        return new TaskDto(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.isCompleted(),
                savedTask.getCompletedAt()
        );
    }

    @Transactional
    public TaskDto editTask(Long ownerId, Long taskId, String title, String description, Boolean completed) {
        TaskEntity task = taskRepository.findByIdAndOwner_Id(taskId, ownerId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id '%d' not found".formatted(taskId)));

        if (title != null && !task.getTitle().equals(title)) {
            task.changeTitle(title);
        }

        if (Objects.equals("", description)) {
            task.clearDescription();
        } else if (description != null && !description.equals(task.getDescription())) {
            task.changeDescription(description);
        }

        if (completed != null && task.isCompleted() != completed) {
            if (completed) {
                task.markAsCompleted(Instant.now());
            } else {
                task.markAsIncomplete();
            }
        }

        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getCompletedAt()
        );
    }

    @Transactional
    public void deleteTask(Long ownerId, Long taskId) {
        TaskEntity task = taskRepository.findByIdAndOwner_Id(taskId, ownerId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id '%d' not found".formatted(taskId)));

        taskRepository.delete(task);
    }
}
