package com.example.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.board.model.CarQna;
import com.example.board.model.Company;
import com.example.board.model.User;
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
  @Autowired
  HttpSession session;

  @GetMapping("/addCar")
  public String addCarType(Model model) {
    List<Company> companys = companyRepository.findAll();
    model.addAttribute("companys", companys);

    User user = (User) session.getAttribute("user_info");
    if (user != null) {
      int userCoins = user.getCoin();
      model.addAttribute("userCoin", userCoins);
    }
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
