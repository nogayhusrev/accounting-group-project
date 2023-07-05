package com.example.accountingproject.repository;

import com.example.accountingproject.entity.InvoiceProduct;
import com.example.accountingproject.entity.Product;
import com.example.accountingproject.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {
    List<InvoiceProduct> findInvoiceProductsByInvoiceId(Long invoiceId);


    List<InvoiceProduct> findAllByInvoice_Id(Long invoiceId);

    List<InvoiceProduct> findInvoiceProductsByInvoiceInvoiceTypeAndProductAndRemainingQuantityNotOrderByIdAsc(InvoiceType type, Product product, Integer remainingQuantity);

    List<InvoiceProduct> findAllInvoiceProductByProductId(Long id);
}
