package com.dawidgorski.todoapp.logic;

import com.dawidgorski.todoapp.model.TaskGroup;
import com.dawidgorski.todoapp.model.TaskGroupRepository;
import com.dawidgorski.todoapp.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {

    @Test
    @DisplayName("Should Throw IllegalState Exception when undone tasks exists")
    void toggleGroup_undoneTasks_Throws_IllegalStateException() {
        //given
        var mockTaskRepository = taskRepositoryReturning(true);
        //system under test
        TaskGroupService toTest = new TaskGroupService(null, mockTaskRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(anyInt()));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("undone tasks");
    }

    private TaskRepository taskRepositoryReturning(boolean b) {
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(b);
        return mockTaskRepository;
    }

    @Test
    @DisplayName("Should Throw IllegalArgument Exception when group not found")
    void toggleGroup_wrongId_ThrowsIllegalArgumentException() {
        //given
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.empty());
        //system under test
        TaskGroupService toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(0));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Group not found");

    }

    @Test
    @DisplayName("Should Toggle Group")
    void toggleGroup_worksAsExpected() {
        //given
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        TaskGroup taskGroup = new TaskGroup();
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.of(taskGroup));
        //system under test
        TaskGroupService toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);
        //when
        toTest.toggleGroup(0);
        //then
        assertThat(taskGroup.isDone()).isTrue();

    }
}