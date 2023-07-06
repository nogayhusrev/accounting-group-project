package com.example.accountingproject.service.impl;

import com.example.accountingproject.dto.InvoiceProductDto;
import com.example.accountingproject.entity.Company;
import com.example.accountingproject.entity.InvoiceProduct;
import com.example.accountingproject.enums.InvoiceStatus;
import com.example.accountingproject.enums.InvoiceType;
import com.example.accountingproject.mapper.MapperUtil;
import com.example.accountingproject.service.InvoiceProductService;
import com.example.accountingproject.service.ReportingService;
import com.example.accountingproject.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportingServiceImpl implements ReportingService {

    private final InvoiceProductService invoiceProductService;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    @Override
    public List<InvoiceProductDto> getStock() {
        Company company = mapperUtil.convert(securityService.getCurrentUser().getCompany(), new Company());
        return invoiceProductService
                .findInvoiceProductsByInvoiceInvoiceStatusAndInvoiceCompanyOrderByIdDesc(InvoiceStatus.APPROVED,company)
                .stream().map(invoiceProduct -> mapperUtil.convert(invoiceProduct,new InvoiceProductDto()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, BigDecimal> getMonthlyProfitLossMap() {
        Map<String, BigDecimal> monthlyProfitLossMap = new HashMap<>();
        List<InvoiceProduct> salesInvoiceProducts = invoiceProductService.findInvoiceProductsByInvoiceType(InvoiceType.SALES);

        for (InvoiceProduct invoiceProduct : salesInvoiceProducts) {
            int year = invoiceProduct.getInvoice().getDate().getYear();
            String month = invoiceProduct.getInvoice().getDate().getMonth().toString();
            BigDecimal profitLoss = invoiceProduct.getProfitLoss();
            String timeWindow = year + " " + month;
            monthlyProfitLossMap.put(timeWindow, monthlyProfitLossMap.getOrDefault(timeWindow, BigDecimal.ZERO).add(profitLoss));
        }
        return monthlyProfitLossMap;
    }
}