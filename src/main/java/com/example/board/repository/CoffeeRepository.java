package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.model.Coffee;

public interface CoffeeRepository extends JpaRepository <Coffee,Integer> {
    
}
