package com.health.yogiodigym.auth.repository;

import com.health.yogiodigym.auth.entity.Member;
import com.health.yogiodigym.auth.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByName(String name);
    List<Member> findByTeam(Team team);
}
