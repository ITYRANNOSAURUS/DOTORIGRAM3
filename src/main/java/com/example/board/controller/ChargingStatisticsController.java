package com.example.board.controller;

import com.example.board.model.ChargerStatus;
import com.example.board.model.ChargerStatusRegion;
import com.example.board.model.ChargerStatusYear;
import com.example.board.model.Chargings;
import com.example.board.model.ExpenseP;
import com.example.board.repository.ChargerStatusRegionRepsoitory;
import com.example.board.repository.ChargerStatusRepository;
import com.example.board.repository.ChargerStatusYearRepsoitory;
import com.example.board.repository.ChargingsRepository;
import com.example.board.repository.ExpenseRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class ChargingStatisticsController {

  @Autowired
  ChargerStatusRepository chargerStatusRepository;

  @Autowired
  ChargerStatusYearRepsoitory chargerStatusYearRepository;

  @Autowired
  ChargerStatusRegionRepsoitory chargerStatusRegionRepository;

  @Autowired
  ExpenseRepository expenseRepository;

  @GetMapping("/statistic/chqRegistser")
  public String chqRegistser(Model model) {
    
    List<ChargerStatusYear> chargerStatusYear = chargerStatusYearRepository.findAll();
      model.addAttribute("Year", chargerStatusYear);
      List<ChargerStatusRegion> chargerStatusRegion = chargerStatusRegionRepository.findAll();
       model.addAttribute("Region", chargerStatusRegion);
      return "chqRegistser";
  }

   @PostMapping("/statistic/save")
  public String addchargings(@ModelAttribute ChargerStatus chargerStatus,
  @RequestParam Long regionId,
  @RequestParam Long yearId, Model model) {

  
  ChargerStatusRegion r = chargerStatusRegionRepository.findById(regionId)
  .orElseThrow(() -> new IllegalArgumentException("Invalid region ID: " + regionId));

  
  ChargerStatusYear y = chargerStatusYearRepository.findById(yearId)
  .orElseThrow(() -> new IllegalArgumentException("Invalid year ID: " + yearId));

  
  Optional<ChargerStatus> existingStatus = chargerStatusRepository.findByChargerStatusRegionAndChargerStatusYear(r, y);
  if (existingStatus.isPresent()) {
    
    ChargerStatus existing = existingStatus.get();
    existing.setQuantity(chargerStatus.getQuantity());
    

    chargerStatusRepository.save(existing);
  }

   return "redirect:/statistic/chqRegistser";
}

// ******
@GetMapping("/statistic/chargerStatus")
  public String chargerStatus(Model model) {
    List<ChargerStatus> chargerStatus = chargerStatusRepository.findAll();
    List<ChargerStatusYear> chargerStatusYear = chargerStatusYearRepository.findAll();
    List<ChargerStatusRegion> chargerStatusRegion = chargerStatusRegionRepository.findAll();

    model.addAttribute("chargerStatus", chargerStatus);
    model.addAttribute("chargerStatusYear", chargerStatusYear);
    model.addAttribute("chargerStatusRegion", chargerStatusRegion);

    Map<String, Map<String, Long>> data = new HashMap<>();
    Map<String, Long> data2 = new HashMap<>();
    for (ChargerStatus status : chargerStatus) {
      String year = status.getChargerStatusYear().getYear();
      long quantity = status.getQuantity();
    
      String region = status.getChargerStatusRegion().getRegion();
      
      if (!data.containsKey(region)) {
        data.put(region, new HashMap<>());
      }

      data.get(region).put(year, quantity);
    }

    model.addAttribute("data", data);

    List<String> regionSet = new ArrayList<>(data.keySet());
    log.error(regionSet.toString());
    model.addAttribute("regionSet", regionSet);

    for (Map<String, Long> value : data.values()) {
      List<String> yearSet = new ArrayList<>(value.keySet());
      List<Long> quantitySet = new ArrayList<>(value.values());

      model.addAttribute("yearSet", yearSet);
      model.addAttribute("quantitySet", quantitySet);

      log.error(yearSet.toString());
      log.error(quantitySet.toString());
    }

    
    // return "chargerStatus3";
     return "chargerStatus4";
    
  }

  // *******
 @GetMapping("/statistic/carStatus")
  public String carStatus(Model model) {
    return "carStatus";
 }

  // *******

@GetMapping("/statistic/chargerStatus5")
  @ResponseBody
  public Map<String, Map<String, Long>> getChargerData() {
    List<ChargerStatus> chargerStatus = chargerStatusRepository.findAll();
    List<ChargerStatusYear> chargerStatusYear = chargerStatusYearRepository.findAll();
    List<ChargerStatusRegion> chargerStatusRegion = chargerStatusRegionRepository.findAll();

    Map<String, Map<String, Long>> data = new HashMap<>();
    // Map<String, Long> data2 = new HashMap<>();

    for (ChargerStatus status : chargerStatus) {
      String year = status.getChargerStatusYear().getYear();
      long quantity = status.getQuantity();
      // data2.put(year, quantity);
      String region = status.getChargerStatusRegion().getRegion();
      // data.put(region, data2);
      if (!data.containsKey(region)) {
        data.put(region, new HashMap<>());
      }

      // Add year and quantity to the correct inner map
      data.get(region).put(year, quantity);
    }

    return data;
  }

  

  @GetMapping("/statistic/chargerStatus7")
  @ResponseBody
  public List<List<Object>> getChargerData3() {
    List<ChargerStatus> chargerStatuses = chargerStatusRepository.findAll();
    List<ChargerStatusYear> chargerStatusYear = chargerStatusYearRepository.findAll();
    List<ChargerStatusRegion> chargerStatusRegion = chargerStatusRegionRepository.findAll();

    return chargerStatuses
      .stream()
      .map(status -> {
        List<Object> list = new ArrayList<>();
        list.add(status.getId());
        list.add(status.getChargerStatusRegion().getRegion());
        list.add(status.getChargerStatusYear().getYear());
        list.add(status.getQuantity());
        return list;
      })
      .collect(Collectors.toList());
  }

  // ******
  
  @GetMapping("/statistic/ChargerService")
  public String ChargerService(Model model) {
    return "ChargerService";
 }

 @GetMapping("/statistic/ChargerExpense")
  public String ChargerExpense(Model model) {
    List<ExpenseP> expense = expenseRepository.findAll();
    model.addAttribute("expense", expense);
    return "ChargerExpense";
 }

   
  

  
}
