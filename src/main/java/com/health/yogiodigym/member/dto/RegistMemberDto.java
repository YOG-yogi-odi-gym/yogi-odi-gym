package com.health.yogiodigym.member.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistMemberDto {
    @NotBlank(message = "이름은 필수입력 값입니다.")
    @Size(min = 2, max = 20, message = "이름은 2~20자 사이여야 합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "이름은 한글 또는 영문만 입력 가능합니다.")
    private String name;

    @Email(message = "올바른 이메일 형식이어야 합니다.")
    @NotBlank(message = "이메일은 필수입력 값입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입력 값입니다.")
    @Pattern(
            regexp = "^(?=.*[가-힣a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}$",
            message = "비밀번호는 6~20자 사이이며, 문자, 숫자, 특수문자를 각각 최소 1개 포함해야 합니다."
    )
    private String pwd;

    @NotBlank(message = "비밀번호 확인은 필수입력 값입니다.")
    private String pwd2;

    @AssertTrue(message = "비밀번호와 비밀번호 확인이 일치하지 않습니다.")
    public boolean isPasswordMatching() {
        return pwd != null && pwd.equals(pwd2);
    }

    @NotBlank(message = "성별을 선택해주세요.")
    private String gender;

    @NotNull(message = "체중을 입력해주세요.")
    @Positive(message = "체중은 양수여야 합니다.")
    private Float weight;

    @NotNull(message = "키를 입력해주세요.")
    @Positive(message = "키는 양수여야 합니다.")
    private Float height;

    private String addr;

    @NotNull(message = "주소를 입력해주세요.")
    private Float latitude;

    private Float longitude;
}
