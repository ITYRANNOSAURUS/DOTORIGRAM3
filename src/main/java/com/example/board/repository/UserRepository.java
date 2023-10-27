package com.example.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.board.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
  public User findByEmailAndPwd(String email, String pwd);
  public User findByEmail(String email);
  public User findById(long id);
  List<User> findByEmailAndNameAndPhone(String email,String name, Integer phone);
}