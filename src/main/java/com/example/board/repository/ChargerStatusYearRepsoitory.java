package com.example.board.repository;

import com.example.board.model.ChargerStatus;
import com.example.board.model.ChargerStatusYear;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargerStatusYearRepsoitory
  extends JpaRepository<ChargerStatusYear, Long> {
  ChargerStatusYear findByYear(String year);
}
