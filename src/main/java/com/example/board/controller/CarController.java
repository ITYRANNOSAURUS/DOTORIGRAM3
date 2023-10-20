package com.example.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.model.Company;
import com.example.board.model.CarType;
import com.example.board.repository.CompanyRepository;
import com.example.board.repository.CarTypeRepository;

@Controller
public class CarController {
  
  @Autowired
   CarTypeRepository carTypeRepository;
  @Autowired
    CompanyRepository companyRepository;

    @GetMapping("/addCar")
    public String addCarType(Model model) {
      List<CarType> carTypes = carTypeRepository.findAll();
       model.addAttribute("carTypes", carTypes);
       List<Company> companys = companyRepository.findAll();
		model.addAttribute("companys", companys);
        return "admin/carform";
    }

    @PostMapping("/addCar")
    public String addCarTypePost(@RequestParam("companyName") String companyName, @RequestParam("name") String name) {
    
      CarType carType = new CarType();
      carType.setName(name);
      carTypeRepository.save(carType);

      Company company = new Company();
      company.setCompanyName(name);
      companyRepository.save(company);
      

        return "redirect:/addcar";
    }
   

}
