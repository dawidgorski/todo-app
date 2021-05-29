package com.dawidgorski.todoapp.logic;

import com.dawidgorski.todoapp.model.TaskGroupRepository;
import com.dawidgorski.todoapp.model.TaskRepository;
import org.springframework.context.annotation.Bean;

public class LogicTaskGroupService {
    @Bean
    TaskGroupService service(
            final TaskGroupRepository repository, final TaskRepository taskRepository
    ){
     return new TaskGroupService(repository,taskRepository);
    }
}
