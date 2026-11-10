package com.portfolio.construction.dto;
import com.portfolio.construction.model.*;
import java.time.LocalDate;
public record TaskResponse(Long id,Long projectId,String title,String description,TaskStatus status,TaskPriority priority,LocalDate dueDate,Long assigneeId,boolean overdue){
 public static TaskResponse from(WorkTask t,LocalDate today){return new TaskResponse(t.getId(),t.getProject().getId(),t.getTitle(),t.getDescription(),t.getStatus(),t.getPriority(),t.getDueDate(),t.getAssignee()==null?null:t.getAssignee().getId(),t.getDueDate()!=null&&t.getDueDate().isBefore(today)&&t.getStatus()!=TaskStatus.DONE);}
}
