package restapi.todoapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restapi.todoapp.dto.request.TodoRequest;
import restapi.todoapp.dto.response.TodoResponse;
import restapi.todoapp.entity.Todo;
import restapi.todoapp.repository.TodoRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private  final TodoRepository todoRepository;

    public TodoResponse insertTodo(TodoRequest todoRequest){
        Todo todo = new Todo();

        todo.setCategoryId(todoRequest.getCategoryId());
        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        todo.setDueDate(Date.valueOf(todoRequest.getDueDate()));

        todo.setIsDeleted(false);
        Todo saveTodo = todoRepository.save(todo);
        TodoResponse response = new TodoResponse();
        response.setId(saveTodo.getId());

        return response;
    }

//    public List<TodoResponse> getAllTodo(){
//        List<Todo> todos = todoRepository.findByIsDeletedFalse();
//
//        List<TodoResponse> todoResponses = new ArrayList<>();
//
//        for (Todo todo : todos){
//            TodoResponse todoResponse = new TodoResponse();
//            todoResponse.setId(todo.getId());
//            todoResponse.setCategory(todo.getCategoryId());
//        }
//    }
}
