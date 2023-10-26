package com.example.board.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class CarData {
    @Id
    Integer id;
    String model;
    String km;
}
