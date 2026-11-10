package com.portfolio.construction.repository;
import com.portfolio.construction.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TaskRepository extends JpaRepository<WorkTask,Long> {
 List<WorkTask> findByProjectIdOrderByIdAsc(Long projectId);
 long countByProjectId(Long projectId);
 long countByProjectIdAndStatus(Long projectId,TaskStatus status);
 void deleteByProjectId(Long projectId);
}
