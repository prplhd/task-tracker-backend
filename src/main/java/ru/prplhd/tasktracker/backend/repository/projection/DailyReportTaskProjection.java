package ru.prplhd.tasktracker.backend.repository.projection;

public interface DailyReportTaskProjection {

    Long getUserId();

    String getEmail();

    String getTaskTitle();

    String getTaskDescription();

    boolean getCompleted();
}
