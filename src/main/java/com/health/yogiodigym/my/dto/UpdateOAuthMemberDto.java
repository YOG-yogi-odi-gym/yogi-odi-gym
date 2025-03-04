package com.health.yogiodigym.my.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOAuthMemberDto {
    @NotBlank(message = "이름은 필수입력 값입니다.")
    @Size(min = 2, max = 20, message = "이름은 2~20자 사이여야 합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "이름은 한글 또는 영문만 입력 가능합니다.")
    private String name;

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
