package com.portfolio.construction.service;
import com.portfolio.construction.dto.ProjectRequest;
import com.portfolio.construction.model.*;
import com.portfolio.construction.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class) class ProjectServiceTest {
 @Mock ProjectRepository projects;
 @Mock TaskRepository tasks;
 @InjectMocks ProjectService service;
 @Test void rejectsDeadlineBeforeStart(){var r=new ProjectRequest("Site A",null,null,LocalDate.of(2026,5,10),LocalDate.of(2026,5,1));assertThrows(IllegalArgumentException.class,()->service.create(r));verifyNoInteractions(projects);}
 @Test void missingProjectReturnsNotFound(){when(projects.findById(42L)).thenReturn(Optional.empty());assertThrows(com.portfolio.construction.exception.NotFoundException.class,()->service.get(42L));}
 @Test void emptyProjectHasZeroProgress(){var p=new Project("Site A",null,null,null);when(projects.save(any(Project.class))).thenAnswer(i->{Project project=i.getArgument(0);try{var f=Project.class.getDeclaredField("id");f.setAccessible(true);f.set(project,1L);}catch(Exception e){throw new RuntimeException(e);}return project;});var result=service.create(new ProjectRequest("Site A",null,null,null,null));assertEquals(0,result.progressPercent());assertEquals(0,result.taskCount());}
}
