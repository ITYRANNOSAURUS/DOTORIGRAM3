package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.board.model.Excel;

public interface ExcelRepository extends JpaRepository< Excel , Integer>{
    
}
