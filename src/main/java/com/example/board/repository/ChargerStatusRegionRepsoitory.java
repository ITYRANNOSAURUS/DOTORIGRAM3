package com.example.board.repository;

import com.example.board.model.ChargerStatusRegion;
import com.example.board.model.ChargerStatusYear;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargerStatusRegionRepsoitory
  extends JpaRepository<ChargerStatusRegion, Long> {
  ChargerStatusRegion findByRegion(String region);
}
