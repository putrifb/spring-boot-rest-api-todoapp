package restapi.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restapi.todoapp.entity.Todo;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByIsDeletedFalse();
}
