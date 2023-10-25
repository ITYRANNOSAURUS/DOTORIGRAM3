package com.example.board.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.board.model.Board;
import com.example.board.model.CarQna;
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
	@ResponseBody
	public String getCoins(Model model) {
		User user = (User) session.getAttribute("user_info");
	
		if (user != null) {
			// 사용자가 오늘 이미 코인을 받았는지 확인합니다.
			Date lastCoinDate = user.getCoinDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
	
			// 오늘 날짜
			int todayYear = calendar.get(Calendar.YEAR);
			int todayMonth = calendar.get(Calendar.MONTH);
			int todayDay = calendar.get(Calendar.DAY_OF_MONTH);
	
			calendar.setTime(lastCoinDate);
	
			// 코인을 마지막으로 받은 날짜
			int lastCoinYear = calendar.get(Calendar.YEAR);
			int lastCoinMonth = calendar.get(Calendar.MONTH);
			int lastCoinDay = calendar.get(Calendar.DAY_OF_MONTH);
	
			// 사용자가 오늘 이미 코인을 받았는지 확인합니다.
			if (todayYear == lastCoinYear && todayMonth == lastCoinMonth && todayDay == lastCoinDay) {
				return "오늘은 이미 획득하셨습니다.";
			} else {
				int userCoins = user.getCoin();
				userCoins += 1;
				user.setCoin(userCoins);
				user.setCoinDate(new Date());
	
				userRepository.save(user);
				return "코인 획득 완료!";
			}
		}
		return "오류 발생";
	}

	@GetMapping("/media/gamepage")
	public String gamepage(Model model) {
		User user = (User) session.getAttribute("user_info");
		if (user != null) {
			Integer userCoins = user.getCoin();
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
			newCoupon.setStartDate(LocalDate.now());
			newCoupon.setEndDate(LocalDate.now().plusMonths(1));

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

	// 쿠폰 사용하기
	@GetMapping("/coupon/remove")
	public String couponRemove(@RequestParam Long id) {
		Optional<Coupon> couponOptional = couponRepository.findById(id);
		if (couponOptional.isPresent()) {
			couponRepository.delete(couponOptional.get());
		}

		return "redirect:/coupon";
	}

}