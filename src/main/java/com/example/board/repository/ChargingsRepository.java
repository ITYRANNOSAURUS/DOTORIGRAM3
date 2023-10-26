package com.example.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.model.Chargings;

public interface ChargingsRepository extends JpaRepository<Chargings, Long> {
    Optional<Chargings> findById(int id);
}


