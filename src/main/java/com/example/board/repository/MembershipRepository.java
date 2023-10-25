package com.example.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.model.Membership;
import com.example.board.model.User;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
   public List<Membership> findByUser(User user);
}