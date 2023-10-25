package com.example.board.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

	@PostMapping("/exchange")
public String exchangeCoin(Model model) {
    User user = (User) session.getAttribute("user_info");

    if (user != null && user.getCoin() >= 10) {
        // 찌리릿코인 10개 차감
        int updatedCoins = user.getCoin() - 10;
        user.setCoin(updatedCoins);

        // 무료충전 쿠폰 생성 및 연결
        Coupon newCoupon = new Coupon();
        newCoupon.setName("무료충전");
        
        // 12자리의 고유 코드 생성
        String uniqueCode = Long.toString(Math.abs(new Random().nextLong()), 36).substring(0, 12);
        newCoupon.setCode(uniqueCode);
        
        newCoupon.setUser(user);

        // 데이터베이스에 저장
        userRepository.save(user);
        couponRepository.save(newCoupon);

        List<Coupon> couponInfo = user.getCoupons();
        model.addAttribute("coupons", couponInfo);
        model.addAttribute("userCoin", updatedCoins);

        model.addAttribute("exchangeSuccess", true); 

        return "redirect:/coupon";
    } else {
        return "redirect:/exchange"; 
    }
}
}