package restapi.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restapi.todoapp.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByIsDeletedFalse();

    Optional<Category> findByIdAndIsDeletedFalse(Long id);
}
