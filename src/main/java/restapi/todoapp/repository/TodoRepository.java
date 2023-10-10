package restapi.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restapi.todoapp.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByIsDeletedFalse();

    Optional<Todo> findByIdAndIsDeletedFalse(Long id);


    Long countByIsDeletedTrueAndCategory_Id(Long categoryId);
}
