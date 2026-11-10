package com.portfolio.construction.service;
import com.portfolio.construction.dto.*;
import com.portfolio.construction.model.Employee;
import com.portfolio.construction.repository.EmployeeRepository;
import com.portfolio.construction.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @Transactional public class EmployeeService {
 private final EmployeeRepository employees;
 public EmployeeService(EmployeeRepository employees){this.employees=employees;}
 public EmployeeResponse create(EmployeeRequest r){return EmployeeResponse.from(employees.save(new Employee(r.name(),r.email(),r.role())));}
 @Transactional(readOnly=true) public List<EmployeeResponse> list(){return employees.findAll().stream().map(EmployeeResponse::from).toList();}
 public Employee find(Long id){return employees.findById(id).orElseThrow(()->new NotFoundException("Employee not found: "+id));}
}
