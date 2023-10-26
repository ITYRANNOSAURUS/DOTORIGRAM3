package com.example.board.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.board.model.Chargings;
import com.example.board.model.User;
import com.example.board.repository.ChargingsRepository;
import com.example.board.repository.UserRepository;

@Controller
public class ChargingsController {

	@Autowired
	ChargingsRepository chargingsRepository;

	 @GetMapping("/stations/find")
	public String stationsFind() {
		return "stationsFind";
	}

	@GetMapping("/chlregister")
	public String chlregister() {
		return "chlregister";
	}

	@PostMapping("/save")
	public String addchargings(@ModelAttribute Chargings chargings) {
		chargingsRepository.save(chargings);
		return "redirect:/chargingslist";
	} 

	@GetMapping("/chargingslist")
	public String getAllchargings(Model model) {
		List<Chargings> chargings = chargingsRepository.findAll();
		model.addAttribute("chargings", chargings);
		return "chargingslist";
	}

	@GetMapping("/chargingsSearch")
	public String chargingsSearch() {
		 return "chargingsSearch";
	}

	@ResponseBody
	@GetMapping("/api/chargingstations")
	public List<Chargings> chargingsSearchfind() {
		return chargingsRepository.findAll();
	}


	@GetMapping("/chargingsDetail/{id}")
	public String chargingsDetail (@PathVariable("id") long id, Model model) {
		Optional<Chargings> Opchargings = chargingsRepository.findById(id);
		if (Opchargings.isPresent()) {
			Chargings ch = Opchargings.get();
			model.addAttribute("ChL", ch);
			return "chargingsDetail";
		} else {
			return "redirect:/charginsglist";
		}
	}
}
