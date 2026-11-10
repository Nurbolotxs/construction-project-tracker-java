package com.portfolio.construction.service;
import org.springframework.beans.factory.annotation.Autowired;
import com.portfolio.construction.dto.*;
import com.portfolio.construction.model.*;
import com.portfolio.construction.repository.TaskRepository;
import com.portfolio.construction.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.List;
@Service @Transactional public class TaskService {
 private final TaskRepository tasks; private final ProjectService projects; private final EmployeeService employees; private final Clock clock;
 @Autowired 
 public TaskService(TaskRepository tasks,ProjectService projects,EmployeeService employees){this(tasks,projects,employees,Clock.systemDefaultZone());}
 TaskService(TaskRepository tasks,ProjectService projects,EmployeeService employees,Clock clock){this.tasks=tasks;this.projects=projects;this.employees=employees;this.clock=clock;}
 public TaskResponse create(Long projectId,TaskRequest r){Project p=projects.find(projectId);Employee e=assignee(r.assigneeId());WorkTask t=new WorkTask(p,r.title(),r.description(),r.priority()==null?TaskPriority.MEDIUM:r.priority(),r.dueDate(),e);if(r.status()!=null)t.update(r.title(),r.description(),r.status(),t.getPriority(),r.dueDate(),e);return response(tasks.save(t));}
 @Transactional(readOnly=true) public List<TaskResponse> list(Long projectId){projects.find(projectId);return tasks.findByProjectIdOrderByIdAsc(projectId).stream().map(this::response).toList();}
 public TaskResponse update(Long projectId,Long taskId,TaskRequest r){projects.find(projectId);WorkTask t=tasks.findById(taskId).orElseThrow(()->new NotFoundException("Task not found: "+taskId));if(!t.getProject().getId().equals(projectId))throw new NotFoundException("Task not found in project: "+taskId);t.update(r.title(),r.description(),r.status()==null?t.getStatus():r.status(),r.priority()==null?t.getPriority():r.priority(),r.dueDate(),assignee(r.assigneeId()));return response(t);}
 public void delete(Long projectId,Long taskId){projects.find(projectId);WorkTask t=tasks.findById(taskId).orElseThrow(()->new NotFoundException("Task not found: "+taskId));if(!t.getProject().getId().equals(projectId))throw new NotFoundException("Task not found in project: "+taskId);tasks.delete(t);}
 private Employee assignee(Long id){return id==null?null:employees.find(id);}
 private TaskResponse response(WorkTask t){return TaskResponse.from(t,LocalDate.now(clock));}
}
