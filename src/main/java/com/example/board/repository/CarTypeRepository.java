package com.example.board.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.board.model.CarType;
import com.example.board.model.Company;

public interface CarTypeRepository extends JpaRepository<CarType, Long> {
  boolean existsByCompanyAndName(Company company, String name);
  List<CarType> findByCompany(Company company);
}
