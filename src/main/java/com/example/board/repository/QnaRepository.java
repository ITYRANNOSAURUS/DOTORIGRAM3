package com.example.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.board.model.Qna;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {
    Page<Qna> findByTitleContaining(String keyword, Pageable pageable);
}