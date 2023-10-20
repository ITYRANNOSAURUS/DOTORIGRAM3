package com.example.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.example.board.model.Qna;
import com.example.board.model.Comment;
import com.example.board.model.FileAtch;
import com.example.board.model.Qna;
import com.example.board.model.User;
import com.example.board.repository.QnaRepository;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.FileAtchRepository;
import com.example.board.repository.QnaRepository;

@Controller
public class QnaController {
	@Autowired
	QnaRepository qnaRepository;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	FileAtchRepository fileAtchRepository;

	@Autowired
	HttpSession session;

	@GetMapping("/qna/image")
	public String image() {
		return "/qna/image";
	}

	@GetMapping("/qna/delete/{id}")
	public String qnaDelete(@PathVariable("id") long id) {
		User loggedUser = (User) session.getAttribute("user_info");
		String loggedName = loggedUser.getEmail();
		Optional<Qna> dbQna = qnaRepository.findById(id);
		String savedName = dbQna.get().getUserId();

		if (savedName.equals(loggedName)) {
			Qna qna = new Qna();
			qna.setId(id);
			qnaRepository.deleteById(id);
			return "redirect:/qna/list";
		} else {

			return "redirect:/qna/view?id=" + id;
		}
	}

	@GetMapping("/qna/update/{id}")
	public String qnaUpdate(Model model, @PathVariable("id") long id) {
		Optional<Qna> data = qnaRepository.findById(id);
		Qna qna = data.get();
		model.addAttribute("qna", qna);
		return "qna/update";
	}

	@PostMapping("/qna/update/{id}")
	public String qnaUpdate(
			@ModelAttribute Qna qna,
			@RequestParam("file") MultipartFile mFile,
			@PathVariable("id") long id) {

		User user = (User) session.getAttribute("user_info");
		if (user == null) {
			return "redirect:/login";
		}
		String userId = user.getEmail();

		Optional<Qna> data = qnaRepository.findById(id);

		if (data.isPresent()) {
			Qna existingQna = data.get();

			if (!userId.equals(existingQna.getUserId())) {
				// 사용자가 게시물의 작성자가 아닌 경우 처리할 내용
				return "redirect:/qna/view?id=" + id; // 예: 게시물 보기 페이지로 리디렉션
			}

			// 게시물 수정 권한이 있는 경우, 업데이트 진행
			existingQna.setTitle(qna.getTitle());
			existingQna.setContent(qna.getContent());

			// 파일 저장 로직 시작
			String originalFilename = mFile.getOriginalFilename();
			FileAtch fileAtch = new FileAtch();
			fileAtch.setOriginalName(originalFilename);
			fileAtch.setSaveName(originalFilename);
			fileAtch.setQna(existingQna);

			fileAtchRepository.save(fileAtch);

			String filename = mFile.getOriginalFilename();

			File file = new File("C:/files/" + filename);
			try {
				mFile.transferTo(file);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			// 파일 저장 로직 끝

			qnaRepository.save(existingQna);
		}

		return "redirect:/qna/" + id;
	}

	@GetMapping("/qna/{id}")
	public String qnaView(Model model, @PathVariable("id") long id) {
		Optional<Qna> data = qnaRepository.findById(id);
		Qna qna = data.get();
		model.addAttribute("qna", qna);
		return "qna/view";
	}

	@GetMapping("/qna/list")
	public String qnaList(Model model,
			@RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "1") int P) {
		Sort sort = Sort.by(Order.desc("id"));
		Pageable pageable = PageRequest.of(P - 1, 10, sort);
		// Page<Qna> list = qnaRepository.findAll(pageable);

		Page<Qna> page;
		if (keyword != null && !keyword.isEmpty()) {
			page = qnaRepository.findByTitleContaining(keyword, pageable);
		} else {
			page = qnaRepository.findAll(pageable);
		}

		List<Qna> list = page.getContent();

		model.addAttribute("list", list);

		int startPageGroup = ((P - 1) / 10) * 10;

		int totalPages = page.getTotalPages();

		// calculate end page
		int startPage = Math.max(1, startPageGroup + 1);
		int endPage = Math.min(totalPages, startPageGroup + 10);

		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("prevGroupStart", Math.max(1, startPage - 10));
		model.addAttribute("nextGroupStart", Math.min(totalPages, startPage + 10));
		model.addAttribute("totalPages", totalPages);

		return "qna/list";
	}

	@GetMapping("/qna/write")
	public String qnaWrite() {

		return "qna/write";
	}

	@PostMapping("/qna/write")
	@Transactional(rollbackFor = { ArithmeticException.class })
	public String qnaWrite(
			@ModelAttribute Qna qna,
			@RequestParam("file") MultipartFile[] mFiles) {

		Qna saveQna = qnaRepository.save(qna);

		User user = (User) session.getAttribute("user_info");
		String userId = user.getEmail();
		qna.setUserId(userId);

		for (MultipartFile mFile : mFiles) {
			String originalFilename = mFile.getOriginalFilename();

			FileAtch fileAtch = new FileAtch();
			fileAtch.setOriginalName(originalFilename);
			fileAtch.setSaveName(originalFilename);
			fileAtch.setQna(saveQna);

			fileAtchRepository.save(fileAtch);

			String filename = mFile.getOriginalFilename();

			File file = new File("C:/files/" + filename);
			try {
				mFile.transferTo(file);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}

		qnaRepository.save(qna);

		return "redirect:/qna/list";
	}

	@PostMapping("/qna/comment")
	public String comment(@ModelAttribute Comment comment, @RequestParam int qnaId) {
		User user = (User) session.getAttribute("user_info");
		String name;

		if (user != null) {
			name = user.getName(); // 로그인한 사용자의 이름을 가져옴
		} else {
			name = "Anonymous"; // 로그인하지 않은 경우에는 "Anonymous"
		}

		comment.setWriter(name);
		comment.setWriter(name);
		comment.setCreDate(new Date());

		Qna qna = new Qna();
		qna.setId(qnaId);
		comment.setQna(qna);

		commentRepository.save(comment);

		return "redirect:/qna/view?id=" + qnaId;
	}

	@GetMapping("/qna/comment/remove")
	public String commentRemove(@ModelAttribute Comment comment, @RequestParam int qnaId) {
		// 1번 new Comment(), setId()
		// 2번 @ModelAttribute Comment comment
		commentRepository.delete(comment);
		return "redirect:/qna/view?id=" + qnaId;
	}

	@GetMapping("/qna/fileAtch/remove")
	public String fileAtchRemove(@ModelAttribute FileAtch fileAtch, @RequestParam int qnaId) {
		// 1번 new Comment(), setId()
		// 2번 @ModelAttribute Comment comment
		fileAtchRepository.delete(fileAtch);
		return "redirect:/qna/view?id=" + qnaId;
	}

	@GetMapping("/qna/view")
	public String view(Model model, @RequestParam long id) {
		Optional<Qna> opt = qnaRepository.findById(id);
		model.addAttribute("qna", opt.get());
		return "qna/view";
	}

}
