package restapi.todoapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import restapi.todoapp.dto.request.TodoRequest;
import restapi.todoapp.dto.response.CategoryResponse;
import restapi.todoapp.dto.response.TodoResponse;
import restapi.todoapp.entity.Category;
import restapi.todoapp.entity.Todo;
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

@Service
@RequiredArgsConstructor
public class TodoService {

    private  final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;

    public TodoResponse insertTodo(TodoRequest todoRequest){
        Todo todo = new Todo();

        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());

        Long categoryId = todoRequest.getCategoryId();
        Optional<Category> categoryOptional = categoryRepository.findByIdAndIsDeletedFalse(categoryId);

        if (categoryOptional.isEmpty()) {
            throw new ResourceNotFoundException("Category not found");
        }

        todo.setCategory(categoryOptional.get());

        LocalDate dueDate = LocalDate.parse(todoRequest.getDueDate()); //parsing string ke localdate
        todo.setDueDate(dueDate);

        ZoneId zoneId = ZoneId.of("Asia/Jakarta");
        ZonedDateTime createdAt = ZonedDateTime.now(zoneId);
        todo.setCreatedAt(createdAt);

        todo.setIsDeleted(false);
        todo.setUpdatedAt(null);
        Todo saveTodo = todoRepository.save(todo);
        TodoResponse response = new TodoResponse();
        response.setId(saveTodo.getId());

        return response;
    }

    public List<TodoResponse> getAllTodo(){
        List<Todo> todos = todoRepository.findByIsDeletedFalse();

        List<TodoResponse> todoResponses = new ArrayList<>();

        for (Todo todo : todos){
            TodoResponse todoResponse = new TodoResponse();
            todoResponse.setId(todo.getId());
            BeanUtils.copyProperties(todo, todoResponse);
            todoResponse.setTitle(todo.getTitle());
            todoResponses.add(todoResponse);
        }

        return todoResponses;
    }

    public TodoResponse getTodoDetail(Long todoId){
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);

        if (optionalTodo.isPresent() && !optionalTodo.get().getIsDeleted()) {
//            Todo todo = optionalTodo.get();
            TodoResponse todoResponse = new TodoResponse();
//            todoResponse.setId(todo.getId());
            BeanUtils.copyProperties(optionalTodo.get(), todoResponse);
//            todoResponse.setTitle(todo.getTitle());
            return todoResponse;
        } else {
            return null;
        }

    }

    public TodoResponse updateTodo(Long todoId, TodoRequest request){
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);

        if (optionalTodo.isPresent()) {
                Todo todo = optionalTodo.get();
//                todo.setCategoryId(category);
                todo.setTitle(request.getTitle());
                todo.setDescription(request.getDescription());
                todo.setDueDate(LocalDate.parse(request.getDueDate()));

                ZoneId zoneId =ZoneId.of("Asia/Jakarta");
                ZonedDateTime updateAt = ZonedDateTime.now(zoneId);
                todo.setUpdatedAt(updateAt);

                Todo updatedTodo = todoRepository.save(todo);
                TodoResponse response = new TodoResponse();
                BeanUtils.copyProperties(updatedTodo, response);
                return response;
            }
        return null;
    }

    public Boolean deleteTodo(Long todoId) {
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);

        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setIsDeleted(true);
            todoRepository.save(todo);
            return true;
        } else {
            return false;
        }
    }

}
