package com.portfolio.construction.controller;
import com.portfolio.construction.dto.*;
import com.portfolio.construction.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/projects") public class ProjectController {
 private final ProjectService service;
 public ProjectController(ProjectService service){this.service=service;}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public ProjectResponse create(@Valid @RequestBody ProjectRequest request){return service.create(request);}
 @GetMapping public List<ProjectResponse> list(){return service.list();}
 @GetMapping("/{id}") public ProjectResponse get(@PathVariable Long id){return service.get(id);}
 @PutMapping("/{id}") public ProjectResponse update(@PathVariable Long id,@Valid @RequestBody ProjectRequest request){return service.update(id,request);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){service.delete(id);}
}
