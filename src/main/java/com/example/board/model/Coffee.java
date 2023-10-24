package com.example.board.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Coffee {
    @Id
    @Column(name="ID")
    int id;
    String coffeestore;
    String address;
    String latitude;
    String longitude;
    String tim;
    String link;
}
