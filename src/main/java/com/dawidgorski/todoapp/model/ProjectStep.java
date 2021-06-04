package com.dawidgorski.todoapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "project_steps")
public class ProjectStep {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @NotBlank(message = "Project step must be not null")
    private String description;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @Column(name = "days_to_deadline")
    private int daysToDeadline;
    public ProjectStep() {
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDaysToDeadline() {
        return daysToDeadline;
    }

    public void setDaysToDeadline(int daysToDeadline) {
        this.daysToDeadline = daysToDeadline;
    }

    public Project getProject() {
        return project;
    }

     public void setProject(final Project project) {
        this.project = project;
    }
}
