package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberShipController {
  
  @GetMapping("/membership")
	public String membership() {
		
		return "membership/membership";
	}

	@GetMapping("/subscribe")
	public String membership2() {
		
		return "membership/subscribe";
	}
  
}
