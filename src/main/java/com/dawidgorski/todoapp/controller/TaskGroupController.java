package com.dawidgorski.todoapp.controller;

import com.dawidgorski.todoapp.logic.TaskGroupService;
import com.dawidgorski.todoapp.model.Task;
import com.dawidgorski.todoapp.model.TaskRepository;
import com.dawidgorski.todoapp.model.projection.GroupReadModel;
import com.dawidgorski.todoapp.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/groups")
class TaskGroupController {
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private final TaskGroupService service;
    private final TaskRepository repository;

    TaskGroupController(final TaskGroupService service, final TaskRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate) {
        GroupReadModel result = service.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping
    ResponseEntity<List<GroupReadModel>> readAllGroups(){
        return ResponseEntity.ok(service.readAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id){
        return ResponseEntity.ok(repository.findAllByGroup_Id(id));
    }

    @Transactional
    @PatchMapping( value = "/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id){
        service.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }
}
