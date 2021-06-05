package com.dawidgorski.todoapp.model.projection;

import com.dawidgorski.todoapp.model.Task;
import com.dawidgorski.todoapp.model.TaskGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class GroupReadModelTest {
 @Test
 @DisplayName("should create null deadline for group when no task deadlines")
 void constructor_noDeadlines_createsNullDeadline(){
     //given
     TaskGroup source = new TaskGroup();
     source.setDescription("foo");
     source.setTasks(Set.of(new Task("bar",null)));
     //when
     GroupReadModel result = new GroupReadModel(source);
     //then
     assertThat(result).hasFieldOrPropertyWithValue("deadline",null);

 }
}