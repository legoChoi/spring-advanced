package org.example.expert.domain.todo.dto;

import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;

public class TodoMapper {

    public static Todo toEntity(TodoSaveRequest request, String weather, User user) {
        return new Todo(
                request.getTitle(),
                request.getContents(),
                weather,
                user
        );
    }

    public static TodoSaveResponse toSaveResponse(Todo todo) {
        User user = todo.getUser();
        return new TodoSaveResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                new UserResponse(user.getId(), user.getEmail())
        );
    }

    public static TodoResponse toTodoResponse(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                new UserResponse(todo.getUser().getId(), todo.getUser().getEmail()),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }
}
