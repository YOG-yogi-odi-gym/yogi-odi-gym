package com.health.yogiodigym.chat.config;

import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final MemberRepository memberRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        String username = (String) accessor.getSessionAttributes().get("username");

        if (StompCommand.CONNECT == accessor.getCommand()) {
            log.info(">>> 사용자가 연결되었습니다. {}", username);
        }

        if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            log.info(">>> 채팅방에 입장하였습니다. {}", username);

            Optional<Member> optionalMember = memberRepository.findByEmail(username);
            if (optionalMember.isEmpty()) {
                log.info("존재하지 않는 회원입니다.");
            }
        }

        if (StompCommand.UNSUBSCRIBE == accessor.getCommand()) {
            log.info(">>> 채팅방과 연결이 끊겼습니다.");
        }

        return message;
    }

}