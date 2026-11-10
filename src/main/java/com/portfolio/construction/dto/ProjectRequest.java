package com.portfolio.construction.dto;
import com.portfolio.construction.model.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
public record ProjectRequest(@NotBlank String name,String description,ProjectStatus status,LocalDate startDate,LocalDate deadline) {}
