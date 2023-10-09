package restapi.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restapi.todoapp.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByIsDeletedFalse();

    List<Todo> findByIsDeletedFalseAndCategory_Id(Long categoryId);

    long countByIsDeletedTrue();

    @Query(
            nativeQuery = true,
            value = "SELECT t.* FROM todo AS t WHERE t.id = :id AND t.is_deleted = false"
    )
    Optional<Todo> findByPrimaryKeyNative(@Param(value = "id") Long primaryKey);

    @Query(
            value = "SELECT t FROM Todo t WHERE t.id = :id AND t.isRemoved IS FALSE "
    )
    Optional<Todo> findByPrimaryKeyJpa(@Param(value = "id") Long primaryKey);
}
