package com.portfolio.construction.model;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity @Table(name="work_tasks") public class WorkTask {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String title;
 @Column(length=2000) private String description;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private TaskStatus status=TaskStatus.TODO;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private TaskPriority priority=TaskPriority.MEDIUM;
 private LocalDate dueDate;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private Project project;
 @ManyToOne(fetch=FetchType.LAZY) private Employee assignee;
 protected WorkTask(){}
 public WorkTask(Project project,String title,String description,TaskPriority priority,LocalDate dueDate,Employee assignee){this.project=project;this.title=title;this.description=description;this.priority=priority;this.dueDate=dueDate;this.assignee=assignee;}
 public Long getId(){return id;} public String getTitle(){return title;} public String getDescription(){return description;} public TaskStatus getStatus(){return status;} public TaskPriority getPriority(){return priority;} public LocalDate getDueDate(){return dueDate;} public Project getProject(){return project;} public Employee getAssignee(){return assignee;}
 public void update(String title,String description,TaskStatus status,TaskPriority priority,LocalDate dueDate,Employee assignee){this.title=title;this.description=description;this.status=status;this.priority=priority;this.dueDate=dueDate;this.assignee=assignee;}
}
