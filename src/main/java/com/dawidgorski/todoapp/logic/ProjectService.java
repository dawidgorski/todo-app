package com.dawidgorski.todoapp.logic;

import com.dawidgorski.todoapp.model.*;
import com.dawidgorski.todoapp.model.projection.GroupReadModel;
import com.dawidgorski.todoapp.model.projection.GroupTaskWriteModel;
import com.dawidgorski.todoapp.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class ProjectService {
    private TaskGroupRepository taskGroupRepository;
    private ProjectRepository projectRepository;
    private TaskConfigurationProperties config;
    private TaskGroupService service;

    public ProjectService(TaskGroupRepository taskGroupRepository, ProjectRepository projectRepository, TaskConfigurationProperties config, TaskGroupService service) {
        this.taskGroupRepository = taskGroupRepository;
        this.projectRepository = projectRepository;
        this.config = config;
        this.service = service;
    }

    public Project save(final Project toSave){
        return projectRepository.save(toSave);
    }

    public List<Project> readAll(){
        return projectRepository.findAll();
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId){
        if(!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
            throw new IllegalStateException("only one undone group in project is allowed");
        }
        return  projectRepository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> {
                                        var task =new GroupTaskWriteModel();
                                        task.setDescription(projectStep.getDescription());
                                        task.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                                        return task;
                                    })
                                    .collect(Collectors.toSet()));
                    return service.createGroup(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("There is no project with given id"));
    }
}
