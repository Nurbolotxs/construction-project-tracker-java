package com.portfolio.construction.controller;
import com.portfolio.construction.dto.*;
import com.portfolio.construction.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/employees") public class EmployeeController {
 private final EmployeeService service;
 public EmployeeController(EmployeeService service){this.service=service;}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public EmployeeResponse create(@Valid @RequestBody EmployeeRequest request){return service.create(request);}
 @GetMapping public List<EmployeeResponse> list(){return service.list();}
}
