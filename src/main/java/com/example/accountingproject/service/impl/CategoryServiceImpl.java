package com.example.accountingproject.service.impl;

import com.example.accountingproject.dto.CategoryDto;
import com.example.accountingproject.entity.Category;
import com.example.accountingproject.entity.Company;
import com.example.accountingproject.mapper.MapperUtil;
import com.example.accountingproject.repository.CategoryRepository;
import com.example.accountingproject.service.CategoryService;
import com.example.accountingproject.service.UserService;
import com.example.accountingproject.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;
    private final UserService userService;
    private final ProductService productService;
    @Override
    public CategoryDto findById(Long categoryId) {
        return mapperUtil.convert(categoryRepository.findById(categoryId).get(), new CategoryDto());
    }

    @Override
    public List<CategoryDto> findAll() {
        Company company = mapperUtil.convert(userService.getCurrentUser().getCompany(), new Company());
        return categoryRepository
                .findAllByCompany(company)
                .stream()
                .sorted(Comparator.comparing(Category::getDescription))
                .map(category -> {
                    CategoryDto categoryDto = mapperUtil.convert(category, new CategoryDto());
                    categoryDto.setEmpty(isTheCategoryEmpty(category));
                    return categoryDto;
                }).collect(Collectors.toList());
    }

    @Override
    public void save(CategoryDto categoryDto) {
        Category category = mapperUtil.convert(categoryDto, new Category());
        Company company = mapperUtil.convert(userService.getCurrentUser().getCompany(), new Company());
        category.setCompany(company);
        categoryRepository.save(category);
    }

    @Override
    public void delete(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        if (isTheCategoryEmpty(category)) {
            category.setIsDeleted(true);
            category.setDescription(category.getDescription() + "_" + category.getId() + "_DELETED");
            categoryRepository.save(category);
        }
    }

    @Override
    public boolean isTheCategoryEmpty(Category category) {
        return productService.findAll().stream()
                .filter(productDto -> productDto.getCategory().getDescription().equalsIgnoreCase(category.getDescription()))
                .count() == 0;
    }

    @Override
    public void update(CategoryDto categoryDto, Long categoryId) {
        categoryDto.setId(categoryId);
        categoryRepository.save(mapperUtil.convert(categoryDto,new Category()));
    }

    @Override
    public boolean isExist(CategoryDto categoryDto) {
        return findAll().stream()
                .filter(category -> category.getDescription().equalsIgnoreCase(categoryDto.getDescription()))
                .count() > 0;
    }

    @Override
    public boolean isExist(CategoryDto categoryDto, Long categoryId) {
        return findAll().stream()
                .filter(category -> category.getDescription().equalsIgnoreCase(categoryDto.getDescription()))
                .filter(category -> Objects.equals(category.getId(), categoryId))
                .count() > 0;

    }
}
