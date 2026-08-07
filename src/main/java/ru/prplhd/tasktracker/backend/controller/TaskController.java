package ru.prplhd.tasktracker.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.prplhd.tasktracker.backend.dto.task.CreateTaskRequestDto;
import ru.prplhd.tasktracker.backend.dto.task.EditTaskRequestDto;
import ru.prplhd.tasktracker.backend.dto.task.TaskDto;
import ru.prplhd.tasktracker.backend.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());

        List<TaskDto> taskDtos = taskService.getTasksByOwnerId(userId);

        return ResponseEntity.ok()
                .body(taskDtos);
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid CreateTaskRequestDto createTaskRequestDto,
                                              @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = Long.valueOf(jwt.getSubject());

        TaskDto taskDto = taskService.createTask(userId, createTaskRequestDto.title(), createTaskRequestDto.description());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskDto);
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskDto> editTask(@PathVariable Long taskId,
                                            @RequestBody @Valid EditTaskRequestDto editTaskRequestDto,
                                            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = Long.valueOf(jwt.getSubject());

        TaskDto taskDto = taskService.editTask(
                userId,
                taskId,
                editTaskRequestDto.title(),
                editTaskRequestDto.description(),
                editTaskRequestDto.completed()
        );

        return ResponseEntity.ok()
                .body(taskDto);
    }
}
