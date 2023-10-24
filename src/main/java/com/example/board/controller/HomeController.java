package com.example.board.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.board.model.Board;
import com.example.board.model.Coupon;
import com.example.board.model.User;

import com.example.board.repository.BoardRepository;
import com.example.board.repository.CouponRepository;
import com.example.board.repository.UserRepository;

@Controller
public class HomeController {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CouponRepository couponRepository;

	@Autowired
	HttpSession session;

	@GetMapping({ "/" })
	public String open() {
		return "open";
	}

	@GetMapping({ "/home" })
	public String index(Model model) {
		List<Board> latestPosts = boardRepository.findTop5ByOrderByIdDesc();
		model.addAttribute("latestPosts", latestPosts);

		// 사용자 정보를 데이터베이스에서 불러옵니다.
		User user = (User) session.getAttribute("user_info");

		if (user != null) {
			// 사용자 정보에서 코인 정보를 불러와 모델에 추가
			Integer userCoins = user.getCoin();
			model.addAttribute("userCoin", userCoins);

			return "index";
		}
		return "index";
	}

	@GetMapping("/media/gamepage")
	public String gamepage() {
		return "/gamepage";
	}

	@GetMapping("/getCoins")
	public String getCoins(Model model) {
		User user = (User) session.getAttribute("user_info");
		if (user != null) {

			int userCoins = user.getCoin();
			userCoins += 1;
			user.setCoin(userCoins);

			userRepository.save(user);

			model.addAttribute("userCoin", userCoins);
		}

		return "redirect:/home";
	}

	@GetMapping("/media/gamepage")
	public String gamepage(Model model) {
		User user = (User) session.getAttribute("user_info");
		if (user != null) {
			int userCoins = user.getCoin();
			model.addAttribute("userCoin", userCoins);
		}
		return "/gamepage";
	}

	@GetMapping("/media/reels")
	public String reels(Model model) {
		User user = (User) session.getAttribute("user_info");
		if (user != null) {
			int userCoins = user.getCoin();
			model.addAttribute("userCoin", userCoins);
		}

		return "/media/reels";
	}

	@GetMapping("/media/game")
	public String game() {
		return "/media/game";
	}

	
	@GetMapping("/coupon")
	public String couponbox(Model model) {
		User user = (User) session.getAttribute("user_info");
		
		if (user != null) {
			int userCoins = user.getCoin();
			model.addAttribute("userCoin", userCoins);
			
			List<Coupon> couponInfo = couponRepository.findByUser(user);
			model.addAttribute("coupons", couponInfo);
		}
		
		return "/media/coupon";
	}
	
	@GetMapping("/exchange")
	public String exchange(Model model) {
		User user = (User) session.getAttribute("user_info");
		if (user != null) {
			int userCoins = user.getCoin();
			model.addAttribute("userCoin", userCoins);
		}

		return "/media/exchange";
	}
}