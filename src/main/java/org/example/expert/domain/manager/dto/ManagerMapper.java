package org.example.expert.domain.manager.dto;

import org.example.expert.domain.manager.dto.response.ManagerResponse;
import org.example.expert.domain.manager.dto.response.ManagerSaveResponse;
import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;

public class ManagerMapper {

    public static Manager toEntity(User managerUser, Todo todo) {
        return new Manager(managerUser, todo);
    }

    public static ManagerSaveResponse toManagerSaveResponse(Manager manager) {
        User user = manager.getUser();
        return new ManagerSaveResponse(
                manager.getId(),
                new UserResponse(user.getId(), user.getEmail())
        );
    }

    public static ManagerResponse toManagerResponse(Manager manager) {
        User user = manager.getUser();
        return new ManagerResponse(
                manager.getId(),
                new UserResponse(user.getId(), user.getEmail())
        );
    }
}
