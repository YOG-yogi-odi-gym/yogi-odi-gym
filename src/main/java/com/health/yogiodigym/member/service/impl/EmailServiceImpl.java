package com.health.yogiodigym.member.service.impl;


import com.health.yogiodigym.common.exception.SendCodeToMailException;
import com.health.yogiodigym.member.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.default-encoding}")
    private String endocing;

    @Value("${spring.mail.username}")
    private String sender;

    private final JavaMailSender javaMailSender;

    @Override
    public void sendCodeToMail(String recipient, String code) {
        MimeMessage message = javaMailSender.createMimeMessage();
        StringBuilder stringForm = new StringBuilder()
                .append("안녕하세요! ")
                .append("이메일 인증을 위한 인증번호를 보내드립니다.<br><br>")
                .append("<h2>인증코드: %s</h2><br><br>")
                .append("위 인증번호를 인증번호 입력창에 입력해주세요.<br>")
                .append("감사합니다.");
        String content = String.format(stringForm.toString(), code);

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,endocing);
            helper.setFrom(sender);
            helper.setTo(recipient);
            helper.setSubject("[YOG] 이메일 인증 코드");
            helper.setText(content,true);
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("이메일 전송 중 오류 발생: {}", e.getMessage(), e);
            throw new SendCodeToMailException();
        }
    }

    @Override
    public String makeCode(){
        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for(int i = 0; i < 6; i++){
            randomNumber.append(random.nextInt(10));
        }

        return randomNumber.toString();
    }
}
