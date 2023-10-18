package com.example.board.controller;

import java.util.List;

import com.example.board.model.Point;
import com.example.board.repository.PointRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MapController {
   @Autowired
   PointRepository pointRepository;

   @GetMapping("/map")
   public String map(Model model) {
      Sort sort = Sort.by(Order.desc("id"));
      List<Point> points = pointRepository.findAll(sort);
      model.addAttribute("points", points);
      return "map/map";
   }

   @PostMapping("/map")
   public String mapPost(@ModelAttribute Point point) {
      pointRepository.save(point);
      return "redirect:/map";
   }

   @GetMapping("/map/search")
   public String search(Model model) {
      Sort sort = Sort.by(Order.desc("id"));
      List<Point> points = pointRepository.findAll(sort);
      model.addAttribute("points", points);
      return "map/search";
   }

   @PostMapping("/map/search")
   public String searchPost() {
      return "redirect:/map/search";
   }

   @GetMapping("/map/delete/{id}")
   public String boardDelete(@PathVariable("id") long id) {
      pointRepository.deleteById(id);
      return "redirect:/map";

   }

   @GetMapping("/map/where")
   public String where() {
      return "/map/where";
   }

}