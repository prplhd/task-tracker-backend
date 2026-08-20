package ru.prplhd.tasktracker.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.prplhd.tasktracker.backend.dto.internal.DailyReportDataDto;
import ru.prplhd.tasktracker.backend.service.DailyReportDataService;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/internal/daily-report-data")
@RequiredArgsConstructor
public class InternalDailyReportDataController {

    private final DailyReportDataService dailyReportDataService;

    @GetMapping
    public List<DailyReportDataDto> getDailyReportData(
            @RequestParam("from") Instant from,
            @RequestParam("to") Instant to
    ) {
        return dailyReportDataService.findDailyReportData(from, to);
    }
}
