package com.example.accountingproject.controller;

import com.example.accountingproject.dto.CategoryDto;
import com.example.accountingproject.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@AllArgsConstructor
@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("categories",categoryService.findAll());
        return "/category/category-list";
    }
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("newCategory", new CategoryDto());
        return "/category/category-create";
    }
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("newCategory") CategoryDto categoryDto, BindingResult bindingResult, Model model) {
        if(categoryService.isExist(categoryDto)) {
            bindingResult.rejectValue("description", " ", "This category already exists.");
        }
        if(bindingResult.hasErrors()) {
            return "/category/category-create";
        }
        categoryService.save(categoryDto);
        return "redirect:/categories/list";

    }

}
