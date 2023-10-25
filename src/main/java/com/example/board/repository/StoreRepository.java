package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.model.Store;
import com.example.board.model.Coffee;

public interface StoreRepository extends JpaRepository < Store,Integer> {
    
}
