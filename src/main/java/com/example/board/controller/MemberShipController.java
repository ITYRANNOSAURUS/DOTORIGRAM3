package com.example.board.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.model.Membership;
import com.example.board.model.User;
import com.example.board.repository.UserRepository;

@Controller
public class MemberShipController {
  @Autowired
	UserRepository userRepository;
	
	@Autowired
	HttpSession session;

  @GetMapping("/membership")
	public String membership() {
		
		return "membership/membership";
	}

	@PostMapping("/membership")
	public ResponseEntity<?> membershipPost(HttpSession session) {
		// 1. Get the user from session
    User user = (User) session.getAttribute("user_info");

		if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  // or any other error status/code
    }

		// 2. Create a new membership
    Membership newMembership = new Membership();
    newMembership.setStartDate(LocalDate.now());
    newMembership.setEndDate(LocalDate.now().plusMonths(1));

		// 3. Add the new membership to the user's memberships
    user.getMemberships().add(newMembership);

		// 4.Don't forget to set the user in the membership as well
    newMembership.setUser(user);

		// Save the updated user (and therefore also the new membership)
  	userRepository.save(user);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/subscribe")
	public String membership2() {
		
		return "membership/subscribe";
	}
  
}
