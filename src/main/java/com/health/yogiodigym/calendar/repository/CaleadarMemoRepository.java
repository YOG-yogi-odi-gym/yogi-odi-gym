package com.health.yogiodigym.calendar.repository;

import com.health.yogiodigym.calendar.entity.CalendarMemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CaleadarMemoRepository extends JpaRepository<CalendarMemo, Long> {

    @Query("SELECT cm FROM CalendarMemo cm WHERE cm.date = :selectedDate AND cm.member.id = :memberId")
    List<CalendarMemo> findMemoByDate(@Param("selectedDate") LocalDate selectedDate, @Param("memberId") Long memberId);

    @Query("SELECT cm FROM CalendarMemo cm WHERE cm.member.id = :memberId")
    List<CalendarMemo> findMemoById(@Param("memberId") Long memberId);


}
