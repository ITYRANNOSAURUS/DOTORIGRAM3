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
public class ChargerStatusYear {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String year;

  @OneToMany(mappedBy = "chargerStatusYear")
  List<ChargerStatus> chargerStatus = new ArrayList<>();
}
