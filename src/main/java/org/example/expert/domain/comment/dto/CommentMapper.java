package org.example.expert.domain.comment.dto;

import org.example.expert.domain.comment.dto.request.CommentSaveRequest;
import org.example.expert.domain.comment.dto.response.CommentResponse;
import org.example.expert.domain.comment.dto.response.CommentSaveResponse;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;

public class CommentMapper {

    public static Comment toEntity(CommentSaveRequest commentSaveRequest, User user, Todo todo) {
        return new Comment(
                commentSaveRequest.getContents(),
                user,
                todo
        );
    }

    public static CommentSaveResponse toCommentSaveResponse(Comment comment) {
        User user = comment.getUser();
        return new CommentSaveResponse(
                comment.getId(),
                comment.getContents(),
                new UserResponse(user.getId(), user.getEmail())
        );
    }

    public static CommentResponse toCommentResponse(Comment comment) {
        User user = comment.getUser();
        return new CommentResponse(
                comment.getId(),
                comment.getContents(),
                new UserResponse(user.getId(), user.getEmail())
        );
    }
}
