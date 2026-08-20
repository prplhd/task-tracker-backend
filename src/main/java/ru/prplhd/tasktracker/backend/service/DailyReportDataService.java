package ru.prplhd.tasktracker.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.prplhd.tasktracker.backend.dto.internal.DailyReportDataDto;
import ru.prplhd.tasktracker.backend.repository.TaskRepository;
import ru.prplhd.tasktracker.backend.repository.projection.DailyReportTaskProjection;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DailyReportDataService {

    private final TaskRepository taskRepository;

    public List<DailyReportDataDto> findDailyReportData(Instant from, Instant to) {

        List<DailyReportTaskProjection> dailyReportTaskProjections = taskRepository.findDailyReportTasks(from, to);

        Map<Long, List<DailyReportTaskProjection>> groupedDailyReportTaskProjections = dailyReportTaskProjections
                .stream()
                .collect(Collectors.groupingBy(DailyReportTaskProjection::getUserId));

        List<DailyReportDataDto> dailyReportDataDtos = new ArrayList<>();

        for (Map.Entry<Long, List<DailyReportTaskProjection>> entry : groupedDailyReportTaskProjections.entrySet()) {
            Long userId = entry.getKey();
            List<DailyReportTaskProjection> tasks = entry.getValue();

            List<DailyReportDataDto.TaskDto> completedTaskDtos = tasks.stream()
                    .filter(task -> task.getCompleted())
                    .map(task -> new DailyReportDataDto.TaskDto(task.getTaskTitle(), task.getTaskDescription()))
                    .toList();

            List<DailyReportDataDto.TaskDto> incompleteTaskDtos = tasks.stream()
                    .filter(task -> !task.getCompleted())
                    .map(task -> new DailyReportDataDto.TaskDto(task.getTaskTitle(), task.getTaskDescription()))
                    .toList();

            DailyReportDataDto dto = new DailyReportDataDto(
                    userId,
                    tasks.getFirst().getEmail(),
                    completedTaskDtos,
                    incompleteTaskDtos
            );

            dailyReportDataDtos.add(dto);
        }

        return dailyReportDataDtos;
    }
}
