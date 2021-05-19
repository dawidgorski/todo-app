package com.dawidgorski.todoapp.controller;

import com.dawidgorski.todoapp.model.Task;
import com.dawidgorski.todoapp.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    public TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping( value ="/tasks", params = {"!sort","!page","!size"})
    ResponseEntity<List<Task>> readAllTasks(){
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping( value ="/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page){
        logger.warn("Custom pager");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping( value ="/tasks/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id){
        if(repository.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        logger.warn("Exposing one task");
        return ResponseEntity.ok(repository.findById(id).get());
    }

    @PutMapping( value = "/tasks/{id}")
    ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task toUpdate){ // requestbody --> zapytanie z formie json, xml albo innej
        if(repository.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> {
                    task.updateFrom(toUpdate);
                    repository.save(task);
                });
        logger.warn("task updated");
        return ResponseEntity.ok(toUpdate);
    }
    @PostMapping(value = "/tasks")
    ResponseEntity<Task> createTask( @RequestBody @Valid Task toCreate){ // requestbody --> zapytanie z formie json, xml albo innej
        repository.save(toCreate);
        Task result =repository.save(toCreate);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }
    @Transactional
    @PatchMapping( value = "/tasks/{id}")
    public ResponseEntity<Task> patchTask(@PathVariable int id){
        if(repository.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.noContent().build();
    }
}
