package com.portfolio.construction.repository;
import com.portfolio.construction.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProjectRepository extends JpaRepository<Project,Long> {
}
