package org.example.expert.domain.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoSaveResponse> saveTodo(
            @Auth AuthUser authUser,
            @Valid @RequestBody TodoSaveRequest todoSaveRequest
    ) {
        TodoSaveResponse data = todoService.saveTodo(authUser, todoSaveRequest);
        return ResponseEntity
                .ok(data);
    }

    @GetMapping
    public ResponseEntity<Page<TodoResponse>> getTodos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<TodoResponse> data = todoService.getTodos(page, size);
        return ResponseEntity
                .ok(data);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponse> getTodo(
            @PathVariable long todoId
    ) {
        TodoResponse data = todoService.getTodo(todoId);
        return ResponseEntity
                .ok(data);
    }
}
