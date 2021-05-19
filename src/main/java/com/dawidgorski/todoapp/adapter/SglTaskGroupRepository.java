package com.dawidgorski.todoapp.adapter;

import com.dawidgorski.todoapp.model.TaskGroup;
import com.dawidgorski.todoapp.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SglTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup,Integer> {
    @Override
    @Query("select distinct g from TaskGroup g join fetch g.tasks")
    List<TaskGroup> findAll();
}
