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

import com.example.board.model.Station;
import com.example.board.model.Store;
import com.example.board.model.Restaurant;
import com.example.board.model.Coffee;
import com.example.board.repository.StationRepository;
import com.example.board.repository.StoreRepository;
import com.example.board.repository.CoffeeRepository;
import com.example.board.repository.RestaurantRepository;

@Controller

public class MapController {
    
    @Autowired
    StationRepository stationRepository;
    @Autowired
    CoffeeRepository coffeeRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    

    @GetMapping("/tetest/data")
    @ResponseBody
    public List<Station> tetestData(){       
       List<Station> excels = stationRepository.findAll();
        return excels;
    }

    @GetMapping("/tetest")
    public String tetest(Model model){
       
       List<Restaurant> excels = restaurantRepository.findAll();
       model.addAttribute("excels", excels);
        return "tetest";
    }
    
   @PostMapping("/tetest")
   public String tetestPost(@ModelAttribute Station excel) {
    //   stationRepository.save(excel);
      return "redirect:/tetest";
   }

   @GetMapping("/map")
   public String map(Model model) {
      List<Station> excel14s = stationRepository.findAll();
      model.addAttribute("excel14s", excel14s);
      return "map";
   }

   @PostMapping("/map")
   public String mapPost(@ModelAttribute Station excel14) {
      stationRepository.save(excel14);
      return "redirect:/map";
   }
    @GetMapping("/coffee/data")
    @ResponseBody
    public List<Coffee> coffeeData(){       
       List<Coffee> shops = coffeeRepository.findAll();
        return shops;
    }
    @GetMapping("/store/data")
    @ResponseBody
    public List<Store> storeData(){       
       List<Store> shops = storeRepository.findAll();
        return shops;
    }
    @GetMapping("/restaurant/data")
    @ResponseBody
    public List<Restaurant> restaurantData(){       
       List<Restaurant> shops = restaurantRepository.findAll();
        return shops;
    }
   @GetMapping("/map/coffee")
   public String coffee(Model model){
      List<Coffee> coffeeshop = coffeeRepository.findAll();
      model.addAttribute("coffeeshop", coffeeshop);
      List<Store> storeshop = storeRepository.findAll();
      model.addAttribute("storeshop", storeshop);
      List<Restaurant> restaurant = restaurantRepository.findAll();
      model.addAttribute("restaurant", restaurant);
      return"/coffee";
   }
   
   @GetMapping("/where")
   public String where(){
      return"where";
   }
   @GetMapping("/chargingSearch")
	public String chargingSearch() {
		return "/chargingSearch";
	}

}
