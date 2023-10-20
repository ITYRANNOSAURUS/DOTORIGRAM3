package com.example.board.controller;




import java.util.List;
import java.util.Optional;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.board.model.Excel;

import com.example.board.repository.ExcelRepository;


@Controller
// @RequestMapping("/login")
public class MapController {
    
    @Autowired
    ExcelRepository excelRepository;
    

    @GetMapping("/tetest/data")
    @ResponseBody
    public List<Excel> tetestData(){       
       List<Excel> excels = excelRepository.findAll();
        return excels;
    }

    @GetMapping("/tetest")
    public String tetest(Model model){
       
       List<Excel> excels = excelRepository.findAll();
       model.addAttribute("excels", excels);
        return "tetest";
    }
    

   @PostMapping("/tetest")
   public String tetestPost(@ModelAttribute Excel excel) {
    //   excelRepository.save(excel);
      return "redirect:/tetest";
   }

   @GetMapping("/viewdetail")
   public String viewdetail(Model model){
      List<Excel> excels = excelRepository.findAll();
       model.addAttribute("excels", excels);
      return "/viewdetail";
   }
   @GetMapping("/map")
   public String map(Model model) {
      // Sort sort = Sort.by(Order.desc("id"));
      List<Excel> Excels = excelRepository.findAll();
      model.addAttribute("Excels", Excels);
      return "/map/map";
   }

   @PostMapping("/map")
   public String mapPost(@ModelAttribute Excel Excel) {
      excelRepository.save(Excel);
      return "redirect:/map";
   }
   @GetMapping("/map/coffee")
   public String coffee(){
      return"/coffee";
   }
}
