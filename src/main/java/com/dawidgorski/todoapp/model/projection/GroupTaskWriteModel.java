package com.dawidgorski.todoapp.model.projection;

import com.dawidgorski.todoapp.model.Task;
import com.dawidgorski.todoapp.model.TaskGroup;

import java.time.LocalDateTime;

public class GroupTaskWriteModel {
    private String description;
    private LocalDateTime deadline;

    public Task toTask(final TaskGroup group){
     return new Task(description,deadline,group);
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }


    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
