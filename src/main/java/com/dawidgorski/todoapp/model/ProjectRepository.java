package com.dawidgorski.todoapp.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    List<Project> findAll();
    Optional<Project> findById(Integer id);
    Project save(Project entity) ;

}
