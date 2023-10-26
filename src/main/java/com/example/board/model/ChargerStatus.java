package com.example.board.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ChargerStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private long quantity;

  @ManyToOne
  ChargerStatusYear chargerStatusYear;

  @ManyToOne
  ChargerStatusRegion chargerStatusRegion;
}
