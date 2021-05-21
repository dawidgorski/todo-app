package com.dawidgorski.todoapp.logic;

import com.dawidgorski.todoapp.model.*;
import com.dawidgorski.todoapp.model.projection.GroupReadModel;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestScope
public class ProjectService {
    private TaskGroupRepository taskGroupRepository;
    private ProjectRepository projectRepository;
    private TaskConfigurationProperties config;

    public ProjectService(final TaskGroupRepository taskGroupRepository, final ProjectRepository projectRepository, final TaskConfigurationProperties config) {
        this.taskGroupRepository = taskGroupRepository;
        this.projectRepository = projectRepository;
        this.config = config;
    }

    public Project createProject(final Project toSave){
        return projectRepository.save(toSave);
    }

    public List<Project> readAll(){
        return projectRepository.findAll();
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId){
        if(!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
            throw new IllegalStateException("only one undone group in project is allowed");
        }
        TaskGroup result = projectRepository.findById(projectId)
                .map(project -> {
                    var targetGroup = new TaskGroup();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> new Task(projectStep.getDescription(), deadline.plusDays(projectStep.getDaysToDeadline()))
                                    ).collect(Collectors.toSet()));
                    return targetGroup;
                }).orElseThrow(() -> new IllegalArgumentException(""));
        return new GroupReadModel(result);
    }
}
