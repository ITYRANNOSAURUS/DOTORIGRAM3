package com.example.board.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Coupon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private LocalDate startDate;
  	private LocalDate endDate;
    private Integer count = 0;
    private boolean used = false;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}