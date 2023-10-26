package com.example.board.repository;

import com.example.board.model.ChargerStatus;
import com.example.board.model.ChargerStatusRegion;
import com.example.board.model.ChargerStatusYear;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ChargerStatusRepository
  extends JpaRepository<ChargerStatus, Long> {
  Optional<ChargerStatus> findById(Long id);
  Optional<ChargerStatus> findByChargerStatusRegionAndChargerStatusYear(ChargerStatusRegion chargerStatusRegion, ChargerStatusYear chargerStatusYear);

  // Long getQuantityByChargerStatusYearAndChargerStatusRegion(
  //   ChargerStatusYear chargerStatusYear,
  //   ChargerStatusRegion chargerStatusRegion
  // );
}
