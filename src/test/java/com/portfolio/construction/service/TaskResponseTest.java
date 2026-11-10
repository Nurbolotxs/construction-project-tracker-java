package com.portfolio.construction.service;
import com.portfolio.construction.dto.TaskResponse;
import com.portfolio.construction.model.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
class TaskResponseTest {
 @Test void overdueOnlyWhenUnfinishedAndPastDue(){Project project=new Project("Site A",null,null,null);WorkTask task=new WorkTask(project,"Foundation",null,TaskPriority.HIGH,LocalDate.of(2025,1,1),null);assertTrue(TaskResponse.from(task,LocalDate.of(2026,1,1)).overdue());task.update("Foundation",null,TaskStatus.DONE,TaskPriority.HIGH,LocalDate.of(2025,1,1),null);assertFalse(TaskResponse.from(task,LocalDate.of(2026,1,1)).overdue());}
}
