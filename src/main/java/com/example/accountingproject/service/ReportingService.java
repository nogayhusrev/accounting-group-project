package com.example.accountingproject.service;

import com.example.accountingproject.dto.InvoiceProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ReportingService {

    List<InvoiceProductDto> getStock();
    Map<String, BigDecimal> getMonthlyProfitLossMap();

}
