package com.example.accountingproject.repository;

import com.example.accountingproject.entity.ClientVendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientVendorRepository extends JpaRepository<ClientVendor, Long> {
}
