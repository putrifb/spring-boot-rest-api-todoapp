package restapi.todoapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.todoapp.dto.request.TodoRequest;
import restapi.todoapp.dto.response.CategoryResponse;
import restapi.todoapp.dto.response.TodoResponse;
import restapi.todoapp.service.TodoService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public void insertTodo(@RequestBody TodoRequest todoRequest){
        todoService.insertTodo(todoRequest);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodo(){
        return ResponseEntity.ok(todoService.getAllTodo());
    }

    @GetMapping(path = "/{todoId}")
    public ResponseEntity<TodoResponse> getTodoByDetail(@PathVariable Long todoId){
        return ResponseEntity.ok(todoService.getTodoDetail(todoId));
    }

    @PutMapping(path = "/{todoId}")
    public ResponseEntity<CategoryResponse> updateTodo(@PathVariable Long todoId, @RequestBody TodoRequest request){
        CategoryResponse response = todoService.updateTodo(todoId, request).getCategory();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{todoId}")
    public ResponseEntity<TodoResponse> deleteTodo(@PathVariable Long todoId) {
        Boolean isDeleted = todoService.deleteTodo(todoId);
        if (isDeleted){
            return ResponseEntity.ok().build();
        }
        return null;
    }
}
