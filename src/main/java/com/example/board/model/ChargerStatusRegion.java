package com.example.board.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class ChargerStatusRegion {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String region;

  @OneToMany(mappedBy = "chargerStatusRegion")
  List<ChargerStatus> chargerStatus2 = new ArrayList<>();
}
