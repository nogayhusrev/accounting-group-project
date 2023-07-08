package com.example.accountingproject.service;

import com.example.accountingproject.dto.CurrencyDto;

import java.math.BigDecimal;
import java.util.Map;

public interface DashboardService {

    Map<String, BigDecimal> getSummaryNumbers();

    CurrencyDto getExchangeRates();
}
