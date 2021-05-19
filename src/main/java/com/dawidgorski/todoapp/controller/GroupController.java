package com.dawidgorski.todoapp.controller;

import com.dawidgorski.todoapp.model.Task;
import com.dawidgorski.todoapp.model.TaskGroup;
import com.dawidgorski.todoapp.model.TaskGroupRepository;
import com.dawidgorski.todoapp.model.TaskRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class GroupController {
    private final TaskGroupRepository repository;

    public GroupController(TaskGroupRepository repository) {
        this.repository = repository;
    }
    @PostMapping(value = "/groups")
    ResponseEntity<TaskGroup> createGroup(@RequestBody @Valid TaskGroup toCreate){ // requestbody --> zapytanie z formie json, xml albo innej
        repository.save(toCreate);
        TaskGroup result =repository.save(toCreate);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }
    @GetMapping( value ="/groups")
    ResponseEntity<List<TaskGroup>> readAllGroups(Pageable page){
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

}
