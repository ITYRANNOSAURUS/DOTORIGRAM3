package com.example.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.model.CarData;
import com.example.board.repository.CarDataRepository;

@RestController
public class CarDataController {

    @Autowired
    private CarDataRepository carRepo;

    @GetMapping("/media/cardata")
    public List<CarData> getAllCars() {
        return carRepo.findAll();
    }
}
