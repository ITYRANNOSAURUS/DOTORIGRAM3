package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.model.Store;
import com.example.board.model.Restaurant;
import com.example.board.model.Coffee;

public interface RestaurantRepository extends JpaRepository < Restaurant,Integer> {
    
}
