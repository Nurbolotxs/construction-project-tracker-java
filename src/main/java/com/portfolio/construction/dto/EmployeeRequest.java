package com.portfolio.construction.dto;
import jakarta.validation.constraints.*;
public record EmployeeRequest(@NotBlank String name,@NotBlank @Email String email,String role) {}
