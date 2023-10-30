package com.example.board.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.model.Membership;
import com.example.board.model.User;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
   public List<Membership> findByUser(User user);
   // findByEndDateBefore 메소드는 endDate가 주어진 날짜보다 이전인 모든 Membership 객체를 반환
   List<Membership> findByEndDateBefore(LocalDate date);
   List<Membership> findByUserId(Long userId);
}