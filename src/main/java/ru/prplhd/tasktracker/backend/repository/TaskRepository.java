package ru.prplhd.tasktracker.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.prplhd.tasktracker.backend.entity.TaskEntity;
import ru.prplhd.tasktracker.backend.repository.projection.DailyReportTaskProjection;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<TaskEntity> findByIdAndOwner_Id(Long taskId, Long ownerId);

    List<TaskEntity> findAllByOwnerIdOrderByIdDesc(Long ownerId);

    @Query("""
    select
        u.id as userId,
        u.email as email,
        t.title as taskTitle,
        t.description as taskDescription,
        t.completed as completed
    from TaskEntity t
    join t.owner u
    where t.completed = false
       or (
           t.completed = true
           and t.completedAt >= :from
           and t.completedAt < :to
       )
    """)
    List<DailyReportTaskProjection> findDailyReportTasks(
            @Param("from") Instant from,
            @Param("to") Instant to
    );
}
