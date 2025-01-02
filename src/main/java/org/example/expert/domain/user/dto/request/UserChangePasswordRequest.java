package org.example.expert.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordRequest {

    @NotBlank @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$", message = "비밀번호는 최소 8자 이상이며, 숫자와 대문자를 포함해야 합니다.")
    private String oldPassword;
    @NotBlank @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$", message = "비밀번호는 최소 8자 이상이며, 숫자와 대문자를 포함해야 합니다.")
    private String newPassword;
}
