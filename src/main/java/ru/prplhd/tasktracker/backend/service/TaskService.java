package ru.prplhd.tasktracker.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prplhd.tasktracker.backend.dto.task.TaskDto;
import ru.prplhd.tasktracker.backend.entity.TaskEntity;
import ru.prplhd.tasktracker.backend.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<TaskDto> getTasksByUserId(Long userId) {
        List<TaskEntity> tasks = taskRepository.findAllByOwnerId(userId);

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
}
