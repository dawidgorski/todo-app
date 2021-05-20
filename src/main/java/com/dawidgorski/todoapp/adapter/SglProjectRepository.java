package com.dawidgorski.todoapp.adapter;

import com.dawidgorski.todoapp.model.Project;
import com.dawidgorski.todoapp.model.ProjectRepository;
import com.dawidgorski.todoapp.model.TaskGroup;
import com.dawidgorski.todoapp.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SglProjectRepository extends ProjectRepository, JpaRepository<Project,Integer> {
    @Override
    @Query("select distinct g from Project g join fetch g.steps")
    List<Project> findAll();

}
