package com.example.accountingproject.repository;


import com.example.accountingproject.entity.Company;
import com.example.accountingproject.entity.Invoice;
import com.example.accountingproject.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findInvoicesByCompanyAndInvoiceType(Company company, InvoiceType invoiceType);
}
