package com.portfolio.construction.model;
import jakarta.persistence.*;
@Entity @Table(name="employees") public class Employee {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Column(nullable=false,unique=true) private String email;
 private String role;
 protected Employee(){}
 public Employee(String name,String email,String role){this.name=name;this.email=email;this.role=role;}
 public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;} public String getRole(){return role;}
}
