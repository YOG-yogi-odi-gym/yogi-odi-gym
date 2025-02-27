package com.health.yogiodigym.member.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistMemberDto {
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @Email(message = "올바른 이메일 형식이어야 합니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 6, max = 20, message = "비밀번호는 6~20자 사이여야 합니다.")
    private String pwd;

    @NotBlank(message = "성별을 입력해주세요!")

    private String gender;

    @NotNull(message = "체중을 입력해주세요!")
    @Positive(message = "체중은 양수여야 합니다.")
    private float weight;

    @NotNull(message = "키을 입력해주세요!")
    @Positive(message = "키는 양수여야 합니다.")
    private float height;

    private String addr;

    @NotNull(message = "주소를 입력해주세요!")
    private float latitude;

    @NotNull(message = "주소를 입력해주세요!")
    private float longitude;
}
