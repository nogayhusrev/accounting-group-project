package com.example.accountingproject.repository;

import com.example.accountingproject.entity.ClientVendor;
import com.example.accountingproject.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface ClientVendorRepository extends JpaRepository<ClientVendor, Long> {
    List<ClientVendor> findAllByCompany(Company company);
}
