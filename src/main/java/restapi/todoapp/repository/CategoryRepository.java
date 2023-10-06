package restapi.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restapi.todoapp.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByIsDeletedFalse();
}
