package com.example.accountingproject.controller;

import com.example.accountingproject.service.ReportingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/reports")
public class ReportingController {

    private final ReportingService reportingService;

    @GetMapping("/stock")
    public String getStock(Model model) {
        model.addAttribute("invoiceProducts", reportingService.getStock());
        return "/report/stock-report";
    }

    @GetMapping("/profit-loss")
    public String getMonthlyProfitLossMap(Model model) {
        model.addAttribute("monthlyProfitLossDataMap", reportingService.getMonthlyProfitLossMap());
        return "/report/profit-loss-report";
    }




}
