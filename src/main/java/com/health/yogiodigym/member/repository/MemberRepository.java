package com.health.yogiodigym.member.repository;

import com.health.yogiodigym.member.auth.MemberStatus;
import com.health.yogiodigym.member.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);

    @Query("SELECT m FROM Member m " +
            "ORDER BY CASE " +
            "WHEN m.status = 'ACTIVE' THEN 1 " +
            "WHEN m.status = 'INCOMPLETE' THEN 2 " +
            "WHEN m.status = 'INACTIVE' THEN 3 " +
            "WHEN m.status = 'SUSPENDED' THEN 4 " +
            "WHEN m.status = 'BAN' THEN 5 " +
            "ELSE 6 END, m.name ASC")
    List<Member> getAllMembers();

    @Query("select m from Member m " +
            "where lower(m.name) like lower(concat('%', :keyword, '%')) " +
            "or lower(substring(m.email, 1, locate('@', m.email)-1)) like lower(concat('%', :keyword, '%')) " +
            "order by case " +
            "when m.status = 'ACTIVE' then 1 " +
            "when m.status = 'INCOMPLETE' then 2 " +
            "when m.status = 'SUSPENDED' then 3 " +
            "when m.status = 'INACTIVE' then 4 " +
            "when m.status = 'BAN' then 5 " +
            "else 6 " +
            "end, m.name asc")
    List<Member> findMembers(@Param("keyword") String keyword);

    @Transactional
    @Modifying
    @Query("UPDATE Member m SET m.status = :status, m.dropDate = CURRENT DATE WHERE m.id IN :memberIds")
    int deleteMembers(@Param("memberIds") List<Long> memberIds, @Param("status") MemberStatus memberStatus);

}
