package com.example.board.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.model.CarQna;


public interface CarQnaRepository extends JpaRepository<CarQna, Long> {
   // Optional<CarQna> findById(int id);
}
