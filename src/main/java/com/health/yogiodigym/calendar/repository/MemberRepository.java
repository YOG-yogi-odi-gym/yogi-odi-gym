package com.health.yogiodigym.calendar.repository;

import com.health.yogiodigym.calendar.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    // 이메일로 memberId만 조회하는 메서드 추가
    @Query("SELECT m.id FROM Member m WHERE m.email = :email")
    Optional<Long> findIdByEmail(@Param("email") String email); // memberId만 조회

}