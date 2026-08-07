package ru.prplhd.tasktracker.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"id", "title", "completed"})
@Table(name = "tasks")
@Entity
public class TaskEntity {

    public TaskEntity(UserEntity owner, String title, String description) {
        this.owner = owner;
        this.title = title;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", updatable = false, nullable = false)
    private UserEntity owner;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private boolean completed = false;

    @Column(name = "completed_at")
    private Instant completedAt;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void clearDescription() {
        this.description = null;
    }

    public void markAsCompleted(Instant completionTime) {
        if (completionTime == null) {
            throw new IllegalArgumentException("Completion time can't be null");
        }

        completed = true;
        completedAt = completionTime;
    }

    public void markAsIncomplete() {
        completed = false;
        completedAt = null;
    }
}
