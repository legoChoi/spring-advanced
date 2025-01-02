package org.example.expert.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.config.PasswordEncoder;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getUser(long userId) {
        User user = getUserById(userId);
        return new UserResponse(user.getId(), user.getEmail());
    }

    @Transactional
    public void changePassword(long userId, UserChangePasswordRequest userChangePasswordRequest) {
        User user = getUserById(userId);
        validateSamePassword(userChangePasswordRequest.getNewPassword(), user.getPassword());
        validatePassword(userChangePasswordRequest.getOldPassword(), user.getPassword());

        String encodedPassword = passwordEncoder.encode(userChangePasswordRequest.getNewPassword());
        user.changePassword(encodedPassword);
    }

    private User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new InvalidRequestException("User not found"));
    }

    private void validateSamePassword(String newPassword, String currentPassword) {
        if (passwordEncoder.matches(newPassword, currentPassword)) {
            throw new InvalidRequestException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
        }
    }

    private void validatePassword(String plainPassword, String encodedPassword) {
        if (!passwordEncoder.matches(plainPassword, encodedPassword)) {
            throw new InvalidRequestException("잘못된 비밀번호입니다.");
        }
    }
}
