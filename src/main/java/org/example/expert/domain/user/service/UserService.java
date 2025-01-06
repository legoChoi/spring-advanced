package org.example.expert.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.config.PasswordEncoder;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.exception.UserNotFoundException;
import org.example.expert.domain.user.exception.UserPasswordMismatchException;
import org.example.expert.domain.user.exception.UserSamePasswordException;
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

        validateIsSamePassword(userChangePasswordRequest.getNewPassword(), user.getPassword());
        validatePasswordMatch(userChangePasswordRequest.getOldPassword(), user.getPassword());

        String encodedPassword = passwordEncoder.encode(userChangePasswordRequest.getNewPassword());
        user.changePassword(encodedPassword);
    }

    public User getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private void validateIsSamePassword(String newPassword, String currentPassword) {
        if (passwordEncoder.matches(newPassword, currentPassword)) {
            throw new UserSamePasswordException();
        }
    }

    private void validatePasswordMatch(String plainPassword, String encodedPassword) {
        if (!passwordEncoder.matches(plainPassword, encodedPassword)) {
            throw new UserPasswordMismatchException();
        }
    }
}
