package restapi.todoapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import restapi.todoapp.dto.request.TodoRequest;
import restapi.todoapp.dto.response.CategoryResponse;
import restapi.todoapp.dto.response.CommonResponse;
import restapi.todoapp.dto.response.TodoResponse;
import restapi.todoapp.dto.response.TodoResponseGetAllTodo;
import restapi.todoapp.entity.Category;
import restapi.todoapp.entity.Todo;
import restapi.todoapp.exception.ResourceBadRequest;
import restapi.todoapp.exception.ResourceNotFoundException;
import restapi.todoapp.repository.CategoryRepository;
import restapi.todoapp.repository.TodoRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private  final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;

    public CommonResponse insertTodo(TodoRequest todoRequest){


        if (todoRequest.getTitle() == null || todoRequest.getTitle().isEmpty()){
            throw new ResourceBadRequest("Title cannot be null or empty.");
        }

        Todo todo = new Todo();
        todo.setTitle(todoRequest.getTitle());

        if (todoRequest.getCategoryId() != null){
            Optional<Category> optionalCategory = categoryRepository.findByIdAndIsDeletedFalse(todoRequest.getCategoryId());

            if (optionalCategory.isEmpty()){
                throw new ResourceNotFoundException("Category not found.");
            }
            todo.setCategory(optionalCategory.get());
        }

        todo.setDescription(todoRequest.getDescription());

        LocalDate dueDate = LocalDate.parse(todoRequest.getDueDate()); //parsing string ke localdate
        todo.setDueDate(dueDate);

        ZoneId zoneId = ZoneId.of("Asia/Jakarta");
        ZonedDateTime createdAt = ZonedDateTime.now(zoneId);
        todo.setCreatedAt(createdAt);

        todo.setIsDeleted(false);
        todo.setUpdatedAt(null);

        Todo saveTodo = todoRepository.save(todo);
        return new CommonResponse(saveTodo.getId());
    }

    private CategoryResponse mapCategoryResponse(Category category){
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setTitle(category.getTitle());
        return categoryResponse;
    }
    private TodoResponseGetAllTodo mapTodoResponse(Todo todo){
        TodoResponseGetAllTodo todoResponse = new TodoResponseGetAllTodo();
        todoResponse.setId(todo.getId());
        todoResponse.setTitle(todo.getTitle());

        if (todo.getCategory() != null){
            todoResponse.setCategory(mapCategoryResponse(todo.getCategory()));
        } else {
            todoResponse.setCategory(null);
        }

        return todoResponse;
    }
    public List<TodoResponseGetAllTodo> getAllTodo(){
        List<Todo> todos = todoRepository.findByIsDeletedFalse();

        return todos.stream().map(this::mapTodoResponse).collect(Collectors.toList());
    }

    public TodoResponse getTodoDetail(Long todoId){
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);

        if (optionalTodo.isEmpty() || optionalTodo.get().getIsDeleted()){
            throw new ResourceNotFoundException("Todo not found with id : " + todoId);
        }

        TodoResponse todoResponse = new TodoResponse();
        todoResponse.setId(optionalTodo.get().getId());
        todoResponse.setTitle(optionalTodo.get().getTitle());
        todoResponse.setDescription(optionalTodo.get().getDescription());
        todoResponse.setDueDate(String.valueOf(optionalTodo.get().getDueDate()));

        if (optionalTodo.get().getCategory() != null){
            todoResponse.setCategory(mapCategoryResponse(optionalTodo.get().getCategory()));
        } else {
            todoResponse.setCategory(null);
        }

        return todoResponse;
    }

    public CommonResponse updateTodo(Long todoId, TodoRequest request){
        Optional<Todo> optionalTodo = todoRepository.findByIdAndIsDeletedFalse(todoId);

        if (optionalTodo.isEmpty()) {
                throw new ResourceNotFoundException("Todo with not found with id : " + todoId);
        }

        optionalTodo.get().setTitle(request.getTitle());
        optionalTodo.get().setDescription(request.getDescription());
        optionalTodo.get().setDueDate(LocalDate.parse(request.getDueDate()));

        ZoneId zoneId =ZoneId.of("Asia/Jakarta");
        ZonedDateTime updateAt = ZonedDateTime.now(zoneId);
        optionalTodo.get().setUpdatedAt(updateAt);

        if (request.getCategoryId() != null){
            Optional<Category> optionalCategory = categoryRepository.findById(request.getCategoryId());
            if (optionalCategory.isEmpty()){
                throw new ResourceNotFoundException("Category not found with id : " + request.getCategoryId());
            }
            optionalTodo.get().setCategory(optionalCategory.get());
        }

        Todo updatedTodo = todoRepository.save(optionalTodo.get());
        return new CommonResponse(updatedTodo.getId());
    }

    public void deleteTodo(Long todoId) {
        Optional<Todo> optionalTodo = todoRepository.findByIdAndIsDeletedFalse(todoId);

        if (optionalTodo.isEmpty()) {
            throw new ResourceNotFoundException("Todo not found with id : " + todoId);
        }

        todoRepository.deleteById(todoId);
    }

}
