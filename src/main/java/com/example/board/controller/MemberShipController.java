package com.example.board.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
	public String membership(Model model) {
		User user = (User) session.getAttribute("user_info");

		if (user != null) {
			List<Membership> memberships = membershipRepository.findByUser(user);
			model.addAttribute("user", user);
			model.addAttribute("memberships", memberships);
		}

		return "membership/membership";
	}

	@PostMapping("/membership")
	public ResponseEntity<?> membershipPost(HttpSession session) {
		// 1. Get the user from session
		User user = (User) session.getAttribute("user_info");

		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
		}

		// 이미 멤버십 회원인 사람은 가입금지
		List<Membership> memberships = membershipRepository.findByUser(user);
    if (!memberships.isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already has a membership");
    }

		// 2. Create a new membership
		Membership newMembership = new Membership();
		newMembership.setStartDate(LocalDate.now());
		newMembership.setEndDate(LocalDate.now().plusMonths(1));
		
		//찌리릿코인 2개지급
		int membershipCoins = user.getCoin();
		membershipCoins += 2;
		user.setCoin(membershipCoins);

		// 3. session user정보 newmembership에 저장
		newMembership.setUser(user);

		// 4. newmembership정보 membershipRepository저장
		membershipRepository.save(newMembership);
		
		// 5. 변경된 코인 수 userRepository에 저장
		userRepository.save(user);
		
		// 6. 세션의 has_membership을 true로 설정
    session.setAttribute("has_membership", true);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/subscribe")
	public String membership2() {

		return "membership/subscribe";
	}

	@GetMapping("/mymembership")
	public String mymembership(Model model) {
		User user = (User) session.getAttribute("user_info");

		if (user != null) {
			List<Membership> memberships = membershipRepository.findByUser(user);
			model.addAttribute("user", user);
			model.addAttribute("memberships", memberships);
		}
		return "membership/mymembership";
	}
	
	@GetMapping("/getcoupon")
	public String getcoupon(Model model) {
		User user = (User) session.getAttribute("user_info");

		if (user != null) {
			List<Membership> memberships = membershipRepository.findByUser(user);
			// if()
			model.addAttribute("user", user);
			model.addAttribute("memberships", memberships);
		}
		return "membership/mymembership";
	}
}
