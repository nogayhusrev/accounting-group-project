package com.example.accountingproject.converter;

import com.example.accountingproject.dto.InvoiceDto;
import com.example.accountingproject.service.InvoiceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;

public class InvoiceDTOConverter implements Converter<String, InvoiceDto> {
    private final InvoiceService invoiceService;


    public InvoiceDTOConverter(@Lazy InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public InvoiceDto convert(String id) {
        if (id == null || id.isBlank())
            return null;
        return invoiceService.findById(Long.parseLong(id));
    }
}
