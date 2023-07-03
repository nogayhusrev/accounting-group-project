package com.example.accountingproject.service.impl;

import com.example.accountingproject.dto.AddressDto;
import com.example.accountingproject.mapper.MapperUtil;
import com.example.accountingproject.repository.AddressRepository;
import com.example.accountingproject.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final MapperUtil mapperUtil;

    public AddressServiceImpl(AddressRepository addressRepository, MapperUtil mapperUtil) {
        this.addressRepository = addressRepository;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public AddressDto findById(Long addressId) {
        throw new IllegalStateException("Not Implemented");
    }

    @Override
    public List<AddressDto> findAll() {
        throw new IllegalStateException("Not Implemented");
    }

    @Override
    public void save(AddressDto addressDto) {
        throw new IllegalStateException("Not Implemented");
    }

    @Override
    public void delete(Long addressId) {

    }

    @Override
    public void update(AddressDto addressDto, Long addressId) {
        throw new IllegalStateException("Not Implemented");
    }

    @Override
    public boolean isExist(AddressDto addressDto) {
        return false;
    }

}