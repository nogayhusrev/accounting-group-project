package com.example.accountingproject.controller;

import com.example.accountingproject.dto.ClientVendorDto;
import com.example.accountingproject.enums.ClientVendorType;
import com.example.accountingproject.service.AddressService;
import com.example.accountingproject.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/clientsVendors")
public class ClientVendorController {
    private final ClientVendorService clientsVendorsService;
    private final AddressService addressService;

    public ClientVendorController(ClientVendorService clientsVendorsService, AddressService addressService) {
        this.clientsVendorsService = clientsVendorsService;
        this.addressService = addressService;
    }


    //list
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("clientVendors", clientsVendorsService.findAll());

        return "clientVendor/clientVendor-list";
    }


    //create
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("newClientVendor", new ClientVendorDto());
        model.addAttribute("clientVendorTypes", new ArrayList<>(Arrays.asList(ClientVendorType.CLIENT, ClientVendorType.VENDOR)));
        model.addAttribute("countries", addressService.getAllCountries());


        return "/clientVendor/clientVendor-create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("newClientVendor") ClientVendorDto clientVendorDto, BindingResult bindingResult, Model model) {
        if (clientsVendorsService.isExist(clientVendorDto)) {
            bindingResult.rejectValue("clientVendorName", "", "This clientVendor already exists");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("clientVendorTypes", new ArrayList<>(Arrays.asList(ClientVendorType.CLIENT, ClientVendorType.VENDOR)));
            model.addAttribute("countries", addressService.getAllCountries());

            return "/clientVendor/clientVendor-create";
        }
        clientsVendorsService.save(clientVendorDto);
        return "redirect:/clientsVendors/list";
    }


    //update
    @GetMapping("/update/{clientVendorId}")
    public String update(@PathVariable("clientVendorId") Long clientVendorId, Model model) {
        model.addAttribute("clientVendor", clientsVendorsService.findById(clientVendorId));
        model.addAttribute("clientVendorTypes", new ArrayList<>(Arrays.asList(ClientVendorType.CLIENT, ClientVendorType.VENDOR)));
        model.addAttribute("countries", addressService.getAllCountries());

        return "/clientVendor/clientVendor-update";
    }

    @PostMapping("/update/{clientVendorId}")
    public String update(@Valid @ModelAttribute("clientVendorId") ClientVendorDto clientVendorDto, BindingResult bindingResult, @PathVariable Long clientVendorId) throws Exception {
        if (clientsVendorsService.isExist(clientVendorDto, clientVendorId)) {
            bindingResult.rejectValue("clientVendorName", "", "This clientVendor already exists");
        }
        if (bindingResult.hasErrors()) {

            return "redirect:/clientsVendors/update/" + clientVendorId;
        }
        clientsVendorsService.update(clientVendorDto, clientVendorId);
        return "redirect:/clientsVendors/list";
    }


    //delete
    @GetMapping("/delete/{clientVendorId}")
    public String delete(@PathVariable("clientVendorId") Long clientVendorId) {
        clientsVendorsService.delete(clientVendorId);
        return "redirect:/clientsVendors/list";
    }
}
