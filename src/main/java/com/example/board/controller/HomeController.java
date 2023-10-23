package com.example.board.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.board.model.Board;
import com.example.board.model.User;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;

@Controller
public class HomeController {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	UserRepository userRepository;

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
			int userCoins = user.getCoin();
			model.addAttribute("userCoin", userCoins);

			return "index";
		}
		return "index";
	}

<<<<<<< HEAD
	@GetMapping("/media/gamepage")
	public String gamepage() {
		return "/gamepage";
=======
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
>>>>>>> 89e453b9bfdd674354db8da922e4f7c47c56cab4
	}

	@GetMapping("/media/reels")
	public String reels() {
		return "/media/reels";
	}

	@GetMapping("/media/game")
	public String game() {
		return "/media/game";
	}

	@GetMapping("/coupon")
	public String coupon(Model model) {
		User user = (User) session.getAttribute("user_info");
		int userCoins = user.getCoin();
		model.addAttribute("userCoin", userCoins);
		return "coupon";
	}

	@PostMapping("/exchangeCoins")
	public ResponseEntity<String> exchangeCoinsForCoupons(@RequestBody Map<String, Integer> request) {
		int numberOfCoinsToExchange = request.get("numberOfCoupons");
		User user = (User) session.getAttribute("user_info");

		if (user != null && user.getCoin() >= numberOfCoinsToExchange * 10) {
			// 코인 차감 및 쿠폰 발급 로직
			int remainingCoins = user.getCoin() - numberOfCoinsToExchange * 10;
			user.setCoin(remainingCoins);
			userRepository.save(user);

			return ResponseEntity.ok("코인 교환 및 쿠폰 발급이 완료되었습니다.");
		}

		return ResponseEntity.badRequest("코인 부족 또는 사용자 인증되지 않음");
	}
}