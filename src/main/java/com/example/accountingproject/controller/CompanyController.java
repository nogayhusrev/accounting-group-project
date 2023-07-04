package com.example.accountingproject.controller;



import com.example.accountingproject.dto.CompanyDto;
import com.example.accountingproject.service.AddressService;
import com.example.accountingproject.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final AddressService addressService;


    public CompanyController(CompanyService companyService, AddressService addressService) {
        this.companyService = companyService;
        this.addressService = addressService;

    }

    @GetMapping("/list")
    public String list(Model model) {

        model.addAttribute("companies", companyService.findAll());

        return "/company/company-list";
    }


    @GetMapping("/create")
    @RolesAllowed("Root")
    public String create(Model model) {


        model.addAttribute("newCompany", new CompanyDto());


        return "/company/company-create";

    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("newCompany") CompanyDto companyDto, BindingResult bindingResult, Model model) {

        if (companyService.isExist(companyDto)) {
            bindingResult.rejectValue("title", " ", "This Company already exists.");
        }


        if (bindingResult.hasErrors()) {

            model.addAttribute("newCompany", new CompanyDto());


            return "company/company-create";
        }

        companyService.save(companyDto);

        return "redirect:/companies/list";

    }

    @GetMapping("/update/{companyId}")
    public String update(@PathVariable("companyId") Long companyId, Model model) {

        model.addAttribute("company", companyService.findById(companyId));

        return "/company/company-update";

    }

    @PostMapping("/update/{companyId}")
    public String update(@Valid @ModelAttribute("company") CompanyDto companyDto, BindingResult bindingResult, @PathVariable Long companyId) throws CloneNotSupportedException {

        if (companyService.isExist(companyDto, companyId)) {
            bindingResult.rejectValue("title", " ", "This title already exists.");
        }

        if (bindingResult.hasErrors()) {


            return "redirect:/companies/update/" + companyId;
        }

        companyService.update(companyDto, companyId);
        return "redirect:/companies/list";
    }

    @GetMapping("/activate/{companyId}")
    public String activate(@PathVariable("companyId") Long companyId) {
        companyService.activate(companyId);
        return "redirect:/companies/list";
    }

    @GetMapping("/deactivate/{companyId}")
    public String deactivate(@PathVariable("companyId") Long companyId) {
        companyService.deactivate(companyId);
        return "redirect:/companies/list";
    }
}
