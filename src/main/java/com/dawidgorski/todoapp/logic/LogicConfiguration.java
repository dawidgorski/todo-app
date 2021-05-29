package com.dawidgorski.todoapp.logic;

import com.dawidgorski.todoapp.model.ProjectRepository;
import com.dawidgorski.todoapp.model.TaskConfigurationProperties;
import com.dawidgorski.todoapp.model.TaskGroupRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
public class LogicConfiguration {
    @Bean
    ProjectService service(
            final TaskGroupRepository taskGroupRepository,
            final ProjectRepository repository,
            final TaskConfigurationProperties config
            ){
        return new ProjectService(taskGroupRepository,repository,config);
    }
}
