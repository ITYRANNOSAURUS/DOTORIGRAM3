package com.example.board.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Chargings {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String city;
	private String location;
	private String address;
	private int fastch;
    private int slowch;
	private String latitude;
	private String longitude;

}
