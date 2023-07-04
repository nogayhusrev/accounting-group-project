package com.example.accountingproject.service;



import com.example.accountingproject.dto.CompanyDto;
import com.example.accountingproject.service.common.CrudService;

import java.util.List;

public interface CompanyService extends CrudService<CompanyDto, Long> {
    void activate(Long companyId);

    void deactivate(Long companyId);

    List<CompanyDto> getCompaniesForCurrentUser();
}
