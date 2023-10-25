package com.example.board.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Store {
    @Id
    @Column(name="ID")
    int id;
    String store;
    String address;
    String latitude;
    String longitude;
    
}
