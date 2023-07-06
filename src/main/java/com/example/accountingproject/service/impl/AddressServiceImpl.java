package com.example.accountingproject.service.impl;

import com.example.accountingproject.client.AddressClient;
import com.example.accountingproject.dto.addressApi.Country;
import com.example.accountingproject.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressClient addressClient;

    public AddressServiceImpl(AddressClient addressClient) {
        this.addressClient = addressClient;
    }


    @Override
    public List<Country> getAllCountries() {

        List<Country> countries = addressClient.getAddressApiCountryStateResponse().getCountries();

        countries = countries.stream().sorted(Comparator.comparing(country -> country.name)).collect(Collectors.toList());

        return countries;
    }
}
