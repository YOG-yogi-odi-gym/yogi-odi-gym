//package com.health.yogiodigym.member.controller.rest;
//
//import com.health.yogiodigym.common.response.HttpResponse;
//import com.health.yogiodigym.member.dto.RegistMemberDto;
//import com.health.yogiodigym.member.entity.MemberOAuth2User;
//import com.health.yogiodigym.member.service.MemberServiceImpl;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import static com.health.yogiodigym.common.message.SuccessMessage.REGIST_SUCCESS;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/member")
//@RequiredArgsConstructor
//public class MemberController {
//
//    private final MemberServiceImpl memberServiceImpl;
//
//    @PostMapping("/regist")
//    public ResponseEntity<HttpResponse> regist(Authentication authentication,
//                                               @ModelAttribute RegistMemberDto registMemberDto,
//                                               @RequestParam(value = "profile", required = false) MultipartFile profile,
//                                               HttpServletRequest request, HttpServletResponse response) {
//
//        if (authentication != null && (authentication.getPrincipal() instanceof MemberOAuth2User principal)) {
//            memberServiceImpl.registWithOAuth2(registMemberDto, principal);
//        } else {
//            memberServiceImpl.registWithEmail(registMemberDto, profile);
//        }
//
//        memberServiceImpl.updateAuthentication(registMemberDto.getEmail(), request, response);
//
//        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, REGIST_SUCCESS.getMessage(), null));
//    }
//}
