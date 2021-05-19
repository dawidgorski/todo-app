package com.dawidgorski.todoapp.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name ="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<TaskGroup> groups;
    @OneToMany(mappedBy = "project")
    private Set<ProjectStep> steps;
    public Project() {
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
}
