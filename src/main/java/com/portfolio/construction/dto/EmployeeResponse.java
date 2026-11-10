package com.portfolio.construction.dto;
import com.portfolio.construction.model.Employee;
public record EmployeeResponse(Long id,String name,String email,String role){public static EmployeeResponse from(Employee e){return new EmployeeResponse(e.getId(),e.getName(),e.getEmail(),e.getRole());}}
