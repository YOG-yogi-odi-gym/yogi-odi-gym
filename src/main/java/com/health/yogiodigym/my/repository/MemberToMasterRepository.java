package com.health.yogiodigym.my.repository;

import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.my.entity.MemberToMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberToMasterRepository extends JpaRepository<MemberToMaster, Long> {
    Optional<MemberToMaster> findByMemberId(Long memberId);

    List<MemberToMaster> findAllByOrderByEnrollDateAsc();
}
