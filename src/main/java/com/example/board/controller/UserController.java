package com.example.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.example.board.model.Board;
import com.example.board.model.CarType;
import com.example.board.model.Company;
import com.example.board.model.Coupon;
import com.example.board.model.Membership;
import com.example.board.model.User;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CarTypeRepository;
import com.example.board.repository.CompanyRepository;
import com.example.board.repository.CouponRepository;
import com.example.board.repository.MembershipRepository;
import com.example.board.repository.UserRepository;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepository;

	@Autowired
	HttpSession session;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	CarTypeRepository carTypeRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	CouponRepository couponRepository;

	@Autowired
	BoardRepository boardRepository;

	@Autowired
	MembershipRepository membershipRepository;

	@GetMapping("/email-check")
	@ResponseBody
	public String emailCheck(@ModelAttribute User user) {
		String email = user.getEmail();

		User result = userRepository.findByEmail(email);

		if (result != null) { // 값 있음, 아이디가 있음, 가입 불가
			return "가입불가";
		} else { // 값 없음, 아이디가 없음, 가입 가능
			return "가입가능";
		}
	}

	@GetMapping("/signin")
	public String signin() {

		return "signin";
	}

	@PostMapping("/signin")
	public String signinPost(@ModelAttribute User user, Model model) {
		User dbUser = (User) userRepository.findByEmail(user.getEmail());
		// 오류
		if (dbUser == null) {
			model.addAttribute("error", "이메일 또는 비밀번호가 일치하지 않습니다.");
			return "signin";
		}
		String encodedPwd = dbUser.getPwd();
	
		String userPwd = user.getPwd();
		boolean isMatch = passwordEncoder.matches(userPwd, encodedPwd);

		if (isMatch) {
			// 로그인 성공한 경우
		  session.setAttribute("user_info", dbUser);
			// 멤버십 정보
			List<Membership> memberships = membershipRepository.findByUser(dbUser);
			if (memberships != null && !memberships.isEmpty()) {
				session.setAttribute("has_membership", true);
			} else {
				session.setAttribute("has_membership", false);
			}

			return "redirect:/";
		} else {
			model.addAttribute("error", "이메일 또는 비밀번호가 일치하지 않습니다.");
			return "signin";
		}
	}

	@GetMapping("/signout")
	public String signout() {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		List<CarType> carTypes = carTypeRepository.findAll();
		model.addAttribute("carTypes", carTypes);
		List<Company> companys = companyRepository.findAll();
		model.addAttribute("companys", companys);
		return "signup";
	}

	@PostMapping("/signup")
	public String signupPost(@ModelAttribute User user,
			@RequestParam(value = "isAdmin", required = false) boolean isAdmin,
			@RequestParam("carname") String carname) {
		String userPwd = user.getPwd();
		String encodePwd = passwordEncoder.encode(userPwd);
		user.setPwd(encodePwd);
		user.setCreDate(new Date());

		// 사용자 역할 설정
		User.UserRole userRole = isAdmin ? User.UserRole.ADMIN : User.UserRole.CUSTOMER;

		// 사용자 정보 저장
		user.setRole(userRole);
		user.setCarname(carname);
		user.setCoin(0);
		user.setCoinDate(new Date(0));

		userRepository.save(user);

		// User 수 증가
		List<User> users = userRepository.findAll();
		int userCount = users.size();
		userCount++;
		for (User u : users) {
			u.setCount(userCount);
		}
		userRepository.saveAll(users);

		return "redirect:/";
	}

	@GetMapping("/mypage")
	public String mypage(Model model, @RequestParam String email) {
		User opt = userRepository.findByEmail(email);
		model.addAttribute("user", opt);
		User user = (User) session.getAttribute("user_info");
		if (user != null) {
			int userCoins = user.getCoin();
			model.addAttribute("userCoin", userCoins);
		}

		return "mypage";
	}

	@PostMapping("/mypage")
	public String updatePost(@ModelAttribute User user) {
		User sessionUser = (User) session.getAttribute("user_info");

		long id = sessionUser.getId();
		User dbUser = userRepository.findById(id);

		String userPwd = user.getPwd();
		String dbPwd = dbUser.getPwd();
		String encodedPwd = passwordEncoder.encode(userPwd);
		if (userPwd.equals(dbPwd)) {
			encodedPwd = userPwd;
		}
		user.setPwd(encodedPwd);
		user.setCreDate(new Date());

		userRepository.save(user);
		return "/signin";
	}

	@GetMapping("/loginnoti")
	public String loginnoti() {
		return "loginnoti";
	}

	@GetMapping("/getCarType")
	@ResponseBody
	public List<CarType> getCarType(String companyName) {
		Company company = companyRepository.findByCompanyName(companyName);
		List<CarType> carTypes = carTypeRepository.findByCompany(company);
		return carTypes;
	}

	// 탈퇴하기
	@GetMapping("/exit")
	public String exit(Model model, HttpSession session) {
    User user = (User) session.getAttribute("user_info");
    Long userId = user.getId();
	
    // membership 테이블에서 해당 사용자와 관련된 데이터 삭제
    List<Membership> memberships = membershipRepository.findByUserId(userId);
    membershipRepository.deleteAll(memberships);
	
	List<Coupon> coupons = couponRepository.findByUserId(userId);
	couponRepository.deleteAll(coupons);

	List<Board> boards = boardRepository.findByUserId(userId);
	boardRepository.deleteAll(boards);

    // user 테이블에서 해당 사용자의 데이터 삭제
    userRepository.delete(user);

    session.invalidate();

    return "redirect:/";
}


	// 비밀번호 변경하기
	@GetMapping("/pwdforget")
	public String pwdforget() {
		return "/pwdforget";
	}

	@PostMapping("/pwdforget")
	@ResponseBody
	public String pwdforgetPost(@RequestBody User user) {
		// 세션에서 사용자 정보 가져오기
    User sessionUser = (User) session.getAttribute("user");
		if (sessionUser != null) {
			String newPassword = user.getNewPassword();
			if (newPassword != null && !newPassword.isEmpty()) {
					String hashedPassword = passwordEncoder.encode(newPassword);
					sessionUser.setPwd(hashedPassword);
					userRepository.save(sessionUser);
					return "비밀번호가 성공적으로 변경되었습니다.";
			} else {
					return "비밀번호 값을 올바르게 입력하십시오.";
			}
	}

	return "비밀번호 변경에 실패하셨습니다.";
	
	}
	
	// 본인 인증하기
	@PostMapping("/validateUser")
	public ResponseEntity<Map<String, Boolean>> validateUser(@RequestBody User user) {
		String email = user.getEmail();
		String name = user.getName();
		Integer phone = user.getPhone();
		List<User> resultList = userRepository.findByEmailAndNameAndPhone(email, name, phone);

		Map<String, Boolean> response = new HashMap<>();
		if (!resultList.isEmpty()) {
			response.put("isValid", true);
			// 세션에 사용자 정보 저장
			session.setAttribute("user", resultList.get(0));
		} else {
			response.put("isValid", false);
		}

		return ResponseEntity.ok(response);
	}

}
