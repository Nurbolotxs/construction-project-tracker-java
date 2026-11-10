package com.portfolio.construction.controller;
import com.portfolio.construction.dto.*;
import com.portfolio.construction.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/projects/{projectId}/tasks") public class TaskController {
 private final TaskService service;
 public TaskController(TaskService service){this.service=service;}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public TaskResponse create(@PathVariable Long projectId,@Valid @RequestBody TaskRequest request){return service.create(projectId,request);}
 @GetMapping public List<TaskResponse> list(@PathVariable Long projectId){return service.list(projectId);}
 @PutMapping("/{taskId}") public TaskResponse update(@PathVariable Long projectId,@PathVariable Long taskId,@Valid @RequestBody TaskRequest request){return service.update(projectId,taskId,request);}
 @DeleteMapping("/{taskId}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long projectId,@PathVariable Long taskId){service.delete(projectId,taskId);}
}
