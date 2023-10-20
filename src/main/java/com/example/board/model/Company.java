package com.example.board.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"carTypes"})    //syso로 출력하려면 쓰는 내용
public class Company implements Serializable{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String companyName;
  
  @JsonIgnore
  @OneToMany(mappedBy = "company")
  List<CarType> carTypes = new ArrayList<>();
  
}
