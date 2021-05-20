package com.dawidgorski.todoapp.logic;

import com.dawidgorski.todoapp.model.TaskConfigurationProperties;
import com.dawidgorski.todoapp.model.TaskGroup;
import com.dawidgorski.todoapp.model.TaskGroupRepository;
import com.dawidgorski.todoapp.model.TaskRepository;
import com.dawidgorski.todoapp.model.projection.GroupReadModel;
import com.dawidgorski.todoapp.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    public TaskGroupService(final TaskGroupRepository repository,final TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source){
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll(){
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }
    public void toggleGroup(int groupId){
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)){
            throw new IllegalStateException("Group has undona tasks. done all the first");
        }
        TaskGroup result = repository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("TaskGroup with give id not found"));
        result.setDone(!result.isDone());

    }
}
