package com.example.board.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.model.CarQna;
import com.example.board.model.CarType;
import com.example.board.model.Chargings;
import com.example.board.model.Company;
import com.example.board.model.Coupon;
import com.example.board.model.User;
import com.example.board.repository.CarQnaRepository;
import com.example.board.repository.CarTypeRepository;
import com.example.board.repository.ChargingsRepository;
import com.example.board.repository.CompanyRepository;
import com.example.board.repository.CouponRepository;
import com.example.board.repository.MembershipRepository;
import com.example.board.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired 
	UserRepository userRepository;

	@Autowired
	HttpSession session;

	@Autowired
	CarQnaRepository carQnaRepository;

	@Autowired
	ChargingsRepository chargingsRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	CarTypeRepository carTypeRepository;

	@Autowired
	MembershipRepository membershipRepository;

	@Autowired
	CouponRepository couponRepository;

  @GetMapping("")
	public String admin(Model model) {
		//유저수 
		long userCount = userRepository.count();  // JPA count() 메소드 활용하여 DB에서 직접 카운트
		model.addAttribute("userCount", userCount);
		
		//구독자수
		long membershipCount = membershipRepository.count();
		model.addAttribute("membershipCount", membershipCount);

		//고객문의 수
		long chargingCount = chargingsRepository.count();
		long carQnaCount = carQnaRepository.count();
		long totalQnaCount = chargingCount+carQnaCount;
		model.addAttribute("totalQnaCount", totalQnaCount);

		//쿠폰 리스트
		 List <Coupon>  coupons =couponRepository.findAll();
		 model.addAttribute("coupons", coupons);

		// 사용자 정보 가져오기
    User user = (User) session.getAttribute("user_info");

    // 사용자의 역할을 확인하여 isAdmin 변수 설정
    boolean isAdmin = user != null && "ADMIN".equals(user.getRole());
    model.addAttribute("isAdmin", isAdmin);
		//차 리스트
		List<CarQna> carQnas = carQnaRepository.findAll();
		model.addAttribute("carQnas", carQnas);
		//충전소 리스트
		List<Chargings> chargings = chargingsRepository.findAll();
		model.addAttribute("chargings", chargings);
	
		return "admin/index";
	}

	//차 등록 삭제
	@GetMapping("/carQna/remove")
	public String carQnaRemove(@RequestParam Long id) {
	  Optional<CarQna> carOptional = carQnaRepository.findById(id);
		if (carOptional.isPresent()) {
			carQnaRepository.delete(carOptional.get());
		}
	
		return "redirect:/admin/";
	}
	
	//차 등록 승인
	@GetMapping("/carQna/approve")
	public String carQnaApprove(@RequestParam Long id) {
		Optional<CarQna> carOptional = carQnaRepository.findById(id);
		
		if (carOptional.isPresent()) {
			CarQna carqna = carOptional.get();

			// Company 찾기
			Company company = companyRepository.findByCompanyName(carqna.getQnacompany());
			if(company == null){
				return "redirect:/error";
			}
			
			// CarType 객체 생성 및 설정
			CarType cartype = new CarType();
			cartype.setName(carqna.getQnacarname());
			cartype.setCompany(company); // 연관관계 설정

		// DB에 저장
		carTypeRepository.save(cartype);
		
		// 기존 CarQNA 삭제 혹은 상태 변경 등 필요한 작업 수행

		return "redirect:/admin/";
		}

		return "redirect:/admin/";
	}
	
	}
