package com.dawidgorski.todoapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Task's description must be not null")
    private String description;
    private boolean done;
    private LocalDateTime deadline;
    @Embedded
    private Audit audit =new Audit();
    @ManyToOne
    @JoinColumn(name = "task_group_id")
    private TaskGroup group;
    public Task() {
    }

    public Task(String description, LocalDateTime deadline) {
        this(description,deadline,null);
    }

    public Task(String description,LocalDateTime deadline, TaskGroup taskGroup) {
        this.description = description;
        this.deadline = deadline;
        if (group!=null){
            this.group=taskGroup;
        }
    }

    public int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(final String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(final boolean done) {
        this.done = done;
    }

    TaskGroup getGroup() {
        return group;
    }
    void setGroup(final TaskGroup group) {
        this.group = group;
    }
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void updateFrom(final Task source){
        this.description = source.description;
        this.done = source.done;
        this.deadline =source.deadline;
        this.group = source.group;
    }

}
