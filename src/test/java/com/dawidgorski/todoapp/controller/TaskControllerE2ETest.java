package com.dawidgorski.todoapp.controller;

import com.dawidgorski.todoapp.model.Task;
import com.dawidgorski.todoapp.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {
    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    TaskRepository repo;

    @Test
    void httpGet_returnsAllTasks(){
        //given
        repo.save(new Task("go to gym", LocalDateTime.now()));
        repo.save(new Task("do 1 udemy chapter ", LocalDateTime.now()));
        //when
        Task[] result = restTemplate.getForObject("http://localhost:" + port + "/tasks", Task[].class);
        //then
        assertThat(result).hasSize(2);
    }

}