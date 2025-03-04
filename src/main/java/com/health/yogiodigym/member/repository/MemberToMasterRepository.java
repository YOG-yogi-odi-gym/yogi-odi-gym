package com.health.yogiodigym.member.repository;


import com.health.yogiodigym.member.entity.MemberToMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberToMasterRepository extends JpaRepository<MemberToMaster, Long> {

    Optional<MemberToMaster> findByMemberId(Long memberId);
}