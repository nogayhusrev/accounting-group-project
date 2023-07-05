package com.example.accountingproject.client;
import com.example.accountingproject.dto.addressApi.AddressApiCountryStateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "address-api", url = "https://countriesnow.space/api/v0.1/countries/states")
public interface AddressClient {


    @GetMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    AddressApiCountryStateResponse getAddressApiCountryStateResponse();




}