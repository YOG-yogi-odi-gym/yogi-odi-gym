package com.health.yogiodigym.auth.repository;

import com.health.yogiodigym.auth.entity.Member;
import com.health.yogiodigym.auth.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
