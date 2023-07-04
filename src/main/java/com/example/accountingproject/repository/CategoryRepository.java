package com.example.accountingproject.repository;

import com.example.accountingproject.entity.Category;
import com.example.accountingproject.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByDescriptionAndCompany(String description, Company company);
    Category findByCompany(Company company);
    List<Category> findAllByCompany(Company company);
}