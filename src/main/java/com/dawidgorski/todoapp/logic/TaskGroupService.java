package com.dawidgorski.todoapp.logic;

import com.dawidgorski.todoapp.model.Project;
import com.dawidgorski.todoapp.model.TaskGroup;
import com.dawidgorski.todoapp.model.TaskGroupRepository;
import com.dawidgorski.todoapp.model.TaskRepository;
import com.dawidgorski.todoapp.model.projection.GroupReadModel;
import com.dawidgorski.todoapp.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.stream.Collectors;


public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    TaskGroupService(final TaskGroupRepository repository, final TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(final GroupWriteModel source) {
        return createGroup(source,null);
    }
    GroupReadModel createGroup(final GroupWriteModel source, final Project project) {
        TaskGroup result = repository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll(){
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }
    public void toggleGroup(int groupId){
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)){
            throw new IllegalStateException("Group has undone tasks. done all the first");
        }
        TaskGroup result = repository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Group not found with given id "));
        result.setDone(!result.isDone());
        repository.save(result);

    }
}
