package com.portfolio.construction.model;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity @Table(name="projects") public class Project {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Column(length=2000) private String description;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private ProjectStatus status=ProjectStatus.PLANNED;
 private LocalDate startDate;
 private LocalDate deadline;
 protected Project() {}
 public Project(String name,String description,LocalDate startDate,LocalDate deadline){this.name=name;this.description=description;this.startDate=startDate;this.deadline=deadline;}
 public Long getId(){return id;} public String getName(){return name;} public String getDescription(){return description;} public ProjectStatus getStatus(){return status;} public LocalDate getStartDate(){return startDate;} public LocalDate getDeadline(){return deadline;}
 public void update(String name,String description,ProjectStatus status,LocalDate startDate,LocalDate deadline){this.name=name;this.description=description;this.status=status;this.startDate=startDate;this.deadline=deadline;}
}
