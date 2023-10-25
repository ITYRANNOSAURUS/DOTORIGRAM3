package com.example.board.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.board.model.Membership;
import com.example.board.model.User;
import com.example.board.repository.MembershipRepository;
import com.example.board.repository.UserRepository;

@Controller
public class MemberShipController {
	@Autowired
	UserRepository userRepository;

	@Autowired
	MembershipRepository membershipRepository;

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
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
		}

		// 2. Create a new membership
		Membership newMembership = new Membership();
		newMembership.setStartDate(LocalDate.now());
		newMembership.setEndDate(LocalDate.now().plusMonths(1));

		// 3. session user정보 newmembership에 저장
		newMembership.setUser(user);

		// 4. newmembership정보 membershipRepository저장
		membershipRepository.save(newMembership);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/subscribe")
	public String membership2() {

		return "membership/subscribe";
	}

	@GetMapping("/mymembership")
	public String mymembership(Model model) {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			model.addAttribute("user", user);
		}
		return "membership/mymembership";
	}
}
