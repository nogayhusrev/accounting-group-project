package com.example.accountingproject.service;

import com.example.accountingproject.dto.ClientVendorDto;
import com.example.accountingproject.dto.InvoiceDto;
import com.example.accountingproject.enums.InvoiceStatus;
import com.example.accountingproject.enums.InvoiceType;
import com.example.accountingproject.service.common.CrudService;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceService extends CrudService<InvoiceDto, Long> {

    List<InvoiceDto> findPurchaseInvoices();

    List<InvoiceDto> findSaleInvoices();

    List<ClientVendorDto> findVendors();

    List<ClientVendorDto> findClients();

    String generateInvoiceNo(InvoiceType invoiceType);

    InvoiceDto getNewInvoice(InvoiceType invoiceType);

    void save(InvoiceDto invoiceDto, InvoiceType invoiceType);

    void approve(Long invoiceId);

    List<InvoiceDto> findLastThreeInvoices();

    List<InvoiceDto> findInvoiceByInvoiceStatus(InvoiceStatus invoiceStatus);

    void printInvoice(Long invoiceId);

    BigDecimal getTotalPriceOfInvoice(Long invoiceId);

    BigDecimal getTotalTaxOfInvoice(Long invoiceId);

    BigDecimal getProfitLossOfInvoice(Long invoiceId);
}
