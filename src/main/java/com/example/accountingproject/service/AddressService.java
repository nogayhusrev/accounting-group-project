package com.example.accountingproject.service;

import com.example.accountingproject.dto.AddressDto;
import com.example.accountingproject.dto.addressApi.Country;
import com.example.accountingproject.mapper.MapperUtil;
import com.example.accountingproject.repository.AddressRepository;
import com.example.accountingproject.service.common.CrudService;

import java.util.List;

public interface AddressService {
    List<Country> getAllCountries();
}
