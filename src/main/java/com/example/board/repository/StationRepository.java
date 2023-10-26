package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.board.model.Station;

public interface StationRepository extends JpaRepository< Station , Integer>{
    
}
