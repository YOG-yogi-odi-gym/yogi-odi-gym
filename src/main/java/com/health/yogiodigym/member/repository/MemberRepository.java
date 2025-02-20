package com.health.yogiodigym.member.repository;

import com.health.yogiodigym.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);

    @Query("select m from Member m " +
            "where lower(m.name) like lower(concat('%', :keyword, '%')) " +
            "or lower(substring(m.email, 1, locate('@', m.email)-1)) like lower(concat('%', :keyword, '%'))")
    List<Member> findMembers(@Param("keyword") String keyword);

}
