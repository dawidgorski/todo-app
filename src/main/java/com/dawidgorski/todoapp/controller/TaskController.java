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
@RequestMapping("/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    public TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping( params = {"!sort","!page","!size"})
    ResponseEntity<List<Task>> readAllTasks(){
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping
    ResponseEntity<List<Task>> readAllTasks(Pageable page){
        logger.warn("Custom pager");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping( value ="/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id){
        var result = repository.findById(id);
        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        logger.warn("Exposing one task");
        return ResponseEntity.ok(result.get());
    }
    @GetMapping("/search/done")
    ResponseEntity <List<Task >>readDoneTasks(@RequestParam(defaultValue = "true") boolean state){
    return ResponseEntity.ok(repository.findByDone(state));
    }
    @PutMapping( value = "/{id}")
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
    @PostMapping
    ResponseEntity<Task> createTask( @RequestBody @Valid Task toCreate){ // requestbody --> zapytanie z formie json, xml albo innej
        repository.save(toCreate);
        Task result =repository.save(toCreate);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }
    @Transactional
    @PatchMapping( value = "/{id}")
    public ResponseEntity<Task> toggleTask(@PathVariable int id){
        if(repository.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.noContent().build();
    }
}
