package com.example.accountingproject.service;

import com.example.accountingproject.dto.InvoiceProductDto;
import com.example.accountingproject.entity.Company;
import com.example.accountingproject.entity.InvoiceProduct;
import com.example.accountingproject.entity.Product;
import com.example.accountingproject.enums.InvoiceStatus;
import com.example.accountingproject.enums.InvoiceType;
import com.example.accountingproject.service.common.CrudService;

import java.util.List;

public interface InvoiceProductService extends CrudService<InvoiceProductDto, Long> {
    List<InvoiceProductDto> findInvoiceProductsByInvoiceId(Long invoiceId);

    List<InvoiceProduct> findInvoiceProductsByInvoiceType(InvoiceType invoiceType);

    void saveInvoiceProductByInvoiceId(InvoiceProductDto invoiceProductDto, Long invoiceId);

    void completeApprovalProcedures(Long invoiceId, InvoiceType type);

    boolean checkProductQuantity(InvoiceProductDto salesInvoiceProduct);

    List<InvoiceProduct> findInvoiceProductsByInvoiceTypeAndProductRemainingQuantity(InvoiceType type, Product product, Integer remainingQuantity);
    List<InvoiceProduct> findAllInvoiceProductsByProductId(Long id);
    List<InvoiceProduct> findInvoiceProductsByInvoiceInvoiceStatusAndInvoiceCompanyOrderByIdDesc(InvoiceStatus status, Company company);

}
