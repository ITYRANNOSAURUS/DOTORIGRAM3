package com.example.board.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.board.model.CarQna;
import com.example.board.model.Company;
import com.example.board.repository.CompanyRepository;
import com.example.board.repository.CarQnaRepository;
import com.example.board.repository.CarTypeRepository;

@Controller
public class CarController {
  
  @Autowired
   CarTypeRepository carTypeRepository;
  @Autowired
   CompanyRepository companyRepository;
  @Autowired
   CarQnaRepository carQnaRepository;

    @GetMapping("/addCar")
    public String addCarType(Model model) {
      List<Company> companys = companyRepository.findAll();
      model.addAttribute("companys", companys);
      return "admin/carform";
    }

    @PostMapping("/addCar")
    public String addCarTypePost(@ModelAttribute CarQna carqna,Model model) {
      List<CarQna> carQnas = carQnaRepository.findAll();
		  model.addAttribute("carQnas", carQnas);
      carQnaRepository.save(carqna);
        return "redirect:/carqnalist";
    }

    @GetMapping("/carqnalist")
	  public String carqnalist(Model model) {
		List<CarQna> carQnas = carQnaRepository.findAll();
		model.addAttribute("carQnas", carQnas);
		return "admin/carqnalist";
	}
   

}
