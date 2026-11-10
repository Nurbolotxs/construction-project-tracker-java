package com.portfolio.construction.dto;
import com.portfolio.construction.model.*;
import java.time.LocalDate;
public record ProjectResponse(Long id,String name,String description,ProjectStatus status,LocalDate startDate,LocalDate deadline,int progressPercent,long taskCount,long completedTasks){
 public static ProjectResponse from(Project p,long count,long done){return new ProjectResponse(p.getId(),p.getName(),p.getDescription(),p.getStatus(),p.getStartDate(),p.getDeadline(),count==0?0:(int)(done*100/count),count,done);}
}
