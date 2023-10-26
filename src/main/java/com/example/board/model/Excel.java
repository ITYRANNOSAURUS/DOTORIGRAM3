package com.example.board.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Excel {
    @Id
    @Column(name = "ID")
    int id;
    String company;
    String name;
    String spotid;
    String spottype;
    String big;
    String small;
    String area;
    String city;
    String address;
    String loc;
    String tim;
    String lim;
    String battery;
    String convi;
    String bigo;
    String latitude;
    String longitude;
    
    
}
