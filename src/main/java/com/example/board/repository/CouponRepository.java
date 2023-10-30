package com.example.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.board.model.Coupon;
import com.example.board.model.Membership;
import com.example.board.model.User;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
  public List<Coupon> findByUser(User user);
  public Coupon findByName(String name);
  public Coupon findByCode(String code);
        Optional<Coupon> findById(long id);
        List<Coupon> findByUserAndUsed(User user, boolean used);
  List<Coupon> findByUserId(Long userId);

}
