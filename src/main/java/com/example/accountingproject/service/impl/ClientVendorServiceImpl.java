package com.example.accountingproject.service.impl;


import com.example.accountingproject.dto.ClientVendorDto;
import com.example.accountingproject.entity.ClientVendor;
import com.example.accountingproject.entity.Company;
import com.example.accountingproject.mapper.MapperUtil;
import com.example.accountingproject.repository.ClientVendorRepository;
import com.example.accountingproject.service.ClientVendorService;
import com.example.accountingproject.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final MapperUtil mapperUtil;
    private final UserService userService;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil, UserService userService) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
    }


    @Override
    public ClientVendorDto findById(Long clientVendorId) {
        return mapperUtil.convert(clientVendorRepository.findById(clientVendorId).get(), new ClientVendorDto());
    }

    @Override
    public List<ClientVendorDto> findAll() {
        Company company = mapperUtil.convert(userService.getCurrentUser().getCompany(), new Company());
        return clientVendorRepository
                .findAllByCompany(company)
                .stream()
                .sorted(Comparator.comparing(ClientVendor::getClientVendorType)
                        .thenComparing(ClientVendor::getClientVendorName))
                .map(each -> mapperUtil.convert(each, new ClientVendorDto()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(ClientVendorDto clientVendorDto) {

        clientVendorDto.setCompany(userService.getCurrentUser().getCompany());
        clientVendorRepository.save(mapperUtil.convert(clientVendorDto, new ClientVendor()));
    }

    @Override
    public void delete(Long clientVendorId) {
        ClientVendor clientVendor = clientVendorRepository.findById(clientVendorId).get();
        clientVendor.setClientVendorName(clientVendor.getClientVendorName() + "_" + clientVendor.getId() + "_DELETED");

        clientVendor.setIsDeleted(true);
        clientVendorRepository.save(clientVendor);
    }


    @Override
    public void update(ClientVendorDto clientVendorDto, Long clientVendorId) {


        clientVendorDto.setId(clientVendorId);

        ClientVendor updatedClientVendor = mapperUtil.convert(clientVendorDto, new ClientVendor());

        clientVendorRepository.save(updatedClientVendor);

    }

    @Override
    public boolean isExist(ClientVendorDto clientVendorDto) {
        return findAll().stream()
                .filter(clientVendor -> clientVendor.getClientVendorName().equalsIgnoreCase(clientVendorDto.getClientVendorName()))
                .count() > 0;
    }

    @Override
    public boolean isExist(ClientVendorDto clientVendorDto, Long aLong) {
        return findAll().stream()
                .filter(clientVendor -> clientVendor.getClientVendorName().equalsIgnoreCase(clientVendorDto.getClientVendorName()))
                .count() > 0;
    }
}