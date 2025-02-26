package com.health.yogiodigym.member.service;

import com.health.yogiodigym.admin.dto.MemberDto.*;
import com.health.yogiodigym.admin.service.AdminService;
import com.health.yogiodigym.admin.service.impl.AdminServiceImpl;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    @DisplayName("회원리스트 조회")
    void testSearchMembers() {
        // given
        List<Member> members = new ArrayList<>();
        members.add(Member.builder().id(1L).name("김지훈").build());
        when(memberRepository.findMembers(anyString())).thenReturn(members);

        // when
        List<MemberResponseDto> response = adminService.searchMembers("김지");

        // then
        assertThat(response.get(0).getName()).isEqualTo("김지훈");
    }


}