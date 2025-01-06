package org.example.expert.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.dto.CommentMapper;
import org.example.expert.domain.comment.dto.request.CommentSaveRequest;
import org.example.expert.domain.comment.dto.response.CommentResponse;
import org.example.expert.domain.comment.dto.response.CommentSaveResponse;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.comment.repository.CommentRepository;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.service.TodoService;
import org.example.expert.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final TodoService todoService;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentSaveResponse saveComment(AuthUser authUser, long todoId, CommentSaveRequest commentSaveRequest) {
        Todo todo = todoService.getTodoById(todoId);
        User user = User.fromAuthUser(authUser);

        Comment newComment = CommentMapper.toEntity(commentSaveRequest, user, todo);
        Comment savedComment = commentRepository.save(newComment);
        return CommentMapper.toCommentSaveResponse(savedComment);
    }

    public List<CommentResponse> getComments(long todoId) {
        List<Comment> commentList = commentRepository.findAllByTodoId(todoId);
        return commentList.stream().map(CommentMapper::toCommentResponse).toList();
    }
}
