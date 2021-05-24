package com.dawidgorski.todoapp.logic;

import com.dawidgorski.todoapp.model.ProjectRepository;
import com.dawidgorski.todoapp.model.TaskConfigurationProperties;
import com.dawidgorski.todoapp.model.TaskGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {
    @Test
    @DisplayName("Should throw IllegalArgument Exception when configuration ok and no projects for A given id")
    void createGroup_configurationOk_And_noProjects_ThrowsIllegalArgumentException() {
        //given
        var mockRepository = mock(ProjectRepository.class);
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        // system under test
        var toTest = new ProjectService(null,mockRepository,mockConfig);
        //when and then

        var exception = catchThrowable(()->toTest.createGroup(LocalDateTime.now(),0));

        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no project");
//        assertThatIllegalStateException()
//                .isThrownBy(() ->toTest.createGroup(LocalDateTime.now(),0) );
    }

    private TaskConfigurationProperties configurationReturning(final boolean result) {
        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(result);
        //and
        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        return mockConfig;
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when configured to allow just 1 group and the other undone group")
    void createGroup_noMultipleGroupsConfig_And_undoneGroupExists_throwsIllegalStateException() {
        //given

        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(true);
        //and
        TaskConfigurationProperties mockConfig = configurationReturning(false);
        // system under test
        var toTest = new ProjectService(mockGroupRepository,null,mockConfig);
        //when and then

        var exception = catchThrowable(()->toTest.createGroup(LocalDateTime.now(),0));

        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("undone group");
    }
    @Test
    @DisplayName("Should throw IllegalArgumentException when configured to allow just 1 group and no groups and projects fo")
    void createGroup_noMultipleGroupsConfig_And_NoUndoneGroupExists_noProject_throwsIllegalArgumentException() {
        //given
        var mockRepository = mock(ProjectRepository.class);
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        //and
        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(false);
        // system under test
        var toTest = new ProjectService(mockGroupRepository,mockRepository,mockConfig);
        //when and then

        var exception = catchThrowable(()->toTest.createGroup(LocalDateTime.now(),0));

        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no project");
    }

    private TaskGroupRepository groupRepositoryReturning(final boolean result) {
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(result);
        return  mockGroupRepository;
    }
    @Test
    @DisplayName("Should create a new group from project")
    void createGroup_ConfigurationOk_existingProject_createsAndSavesGroup(){


    }

}