package com.portfolio.construction.dto;
import com.portfolio.construction.model.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
public record TaskRequest(@NotBlank String title,String description,TaskStatus status,TaskPriority priority,LocalDate dueDate,Long assigneeId) {}
