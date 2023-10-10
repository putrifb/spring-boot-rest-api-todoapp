package restapi.todoapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.todoapp.dto.request.TodoRequest;
//import restapi.todoapp.dto.response.CategoryResponse;
import restapi.todoapp.dto.response.CommonResponse;
import restapi.todoapp.dto.response.TodoResponse;
import restapi.todoapp.dto.response.TodoResponseGetAllTodo;
import restapi.todoapp.service.TodoService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<CommonResponse> insertTodo(@RequestBody TodoRequest todoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.insertTodo(todoRequest));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseGetAllTodo>> getAllTodo(){
        return ResponseEntity.ok(todoService.getAllTodo());
    }

    @GetMapping(path = "/{todoId}")
    public ResponseEntity<TodoResponse> getTodoByDetail(@PathVariable Long todoId){
        return ResponseEntity.ok(todoService.getTodoDetail(todoId));
    }

    @PutMapping(path = "/{todoId}")
    public ResponseEntity<CommonResponse> updateTodo(@PathVariable Long todoId, @RequestBody TodoRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.updateTodo(todoId, request));
    }

    @DeleteMapping(path = "/{todoId}")
    public ResponseEntity<CommonResponse> deleteTodo(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok(new CommonResponse());
    }
}
