package com.example.board.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String email;
	private String pwd;
	private String name;
	private Integer phone;
	private Date creDate;
	private String carname;
	@Column(nullable = true)
	private Integer count;
	private UserRole role;

	public enum UserRole {
		CUSTOMER, // 일반 고객0
		ADMIN // 관리자1
	}

	public String getRole() {
		return role.name(); // 열거형 값의 이름을 문자열로 반환
	}

	private Integer coin;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
	List<Board> boards = new ArrayList<>();

	public List<Qna> getQnas() {
		return null;
	}

	@OneToMany(mappedBy = "user")
    private List<Membership> memberships;
}