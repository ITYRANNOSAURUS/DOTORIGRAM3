package com.example.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.model.CarQna;
import com.example.board.model.User;
import com.example.board.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

  @Autowired
	UserRepository userRepository;

	@Autowired
	HttpSession session;

  @GetMapping("")
	public String admin(Model model) {
		//유저수 
		List<User> users= userRepository.findAll();
    int userCount = users.size();
    model.addAttribute("userCount", userCount);

		// 사용자 정보 가져오기
    User user = (User) session.getAttribute("user_info");

    // 사용자의 역할을 확인하여 isAdmin 변수 설정
    boolean isAdmin = user != null && "ADMIN".equals(user.getRole());
    model.addAttribute("isAdmin", isAdmin);
		
	
		return "admin/index";
	}


	}
