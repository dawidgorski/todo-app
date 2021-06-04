package com.dawidgorski.todoapp.logic;

import com.dawidgorski.todoapp.model.ProjectRepository;
import com.dawidgorski.todoapp.model.TaskConfigurationProperties;
import com.dawidgorski.todoapp.model.TaskGroupRepository;
import com.dawidgorski.todoapp.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
public class LogicConfiguration {
    @Bean
    ProjectService projectService(
            final TaskGroupRepository taskGroupRepository,
            final ProjectRepository repository,
            final TaskConfigurationProperties config,
            final TaskGroupService taskGroupService
            ){
        return new ProjectService(taskGroupRepository,repository,config,taskGroupService);
    }
    @Bean
    TaskGroupService taskGroupService(
            final TaskGroupRepository taskGroupRepository,
            final TaskRepository taskRepository
    ) {
        return new TaskGroupService(taskGroupRepository, taskRepository);
    }
}
