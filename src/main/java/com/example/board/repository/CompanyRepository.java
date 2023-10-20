package com.example.board.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
  Company findByCompanyName(String companyName);
}
