package com.portfolio.construction.service;
import com.portfolio.construction.dto.*;
import com.portfolio.construction.model.*;
import com.portfolio.construction.repository.*;
import com.portfolio.construction.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @Transactional public class ProjectService {
 private final ProjectRepository projects; private final TaskRepository tasks;
 public ProjectService(ProjectRepository projects,TaskRepository tasks){this.projects=projects;this.tasks=tasks;}
 public ProjectResponse create(ProjectRequest r){validateDates(r.startDate(),r.deadline());Project p=new Project(r.name(),r.description(),r.startDate(),r.deadline());if(r.status()!=null)p.update(r.name(),r.description(),r.status(),r.startDate(),r.deadline());return summary(projects.save(p));}
 @Transactional(readOnly=true) public List<ProjectResponse> list(){return projects.findAll().stream().map(this::summary).toList();}
 @Transactional(readOnly=true) public ProjectResponse get(Long id){return summary(find(id));}
 public ProjectResponse update(Long id,ProjectRequest r){validateDates(r.startDate(),r.deadline());Project p=find(id);p.update(r.name(),r.description(),r.status()==null?p.getStatus():r.status(),r.startDate(),r.deadline());return summary(p);}
 public void delete(Long id){find(id);tasks.deleteByProjectId(id);projects.deleteById(id);}
 public Project find(Long id){return projects.findById(id).orElseThrow(()->new NotFoundException("Project not found: "+id));}
 private ProjectResponse summary(Project p){return ProjectResponse.from(p,tasks.countByProjectId(p.getId()),tasks.countByProjectIdAndStatus(p.getId(),TaskStatus.DONE));}
 private void validateDates(java.time.LocalDate start,java.time.LocalDate end){if(start!=null&&end!=null&&end.isBefore(start))throw new IllegalArgumentException("Deadline must not precede start date");}
}
