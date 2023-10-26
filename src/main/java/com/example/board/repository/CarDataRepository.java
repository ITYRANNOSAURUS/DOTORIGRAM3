package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.model.CarData;

public interface CarDataRepository extends JpaRepository<CarData, Integer> {
}
