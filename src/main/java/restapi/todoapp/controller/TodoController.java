package restapi.todoapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restapi.todoapp.dto.request.TodoRequest;
import restapi.todoapp.service.TodoService;

@RestController
@RequestMapping(path = "/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public void insertTodo(@RequestBody TodoRequest todoRequest){
        todoService.insertTodo(todoRequest);
    }
}
