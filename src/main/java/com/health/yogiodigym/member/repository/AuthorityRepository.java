package com.health.yogiodigym.member.repository;

import com.health.yogiodigym.member.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    boolean existsByMemberId(Long memberId);
}
