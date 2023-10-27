package com.example.board.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ExpenseP {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double springOff;
    private double springMid;
    private double springPeak;
    private double summarOff;
    private double summarMid;
    private double summarPeak;
    private double winterOff;
    private double winterMid;
    private double winterPeak;

}



