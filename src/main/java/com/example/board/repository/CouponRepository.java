package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.board.model.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
  public Coupon findByName(String name);
  public Coupon findByCode(String code);

}
