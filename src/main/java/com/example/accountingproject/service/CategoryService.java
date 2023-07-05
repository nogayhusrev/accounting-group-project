package com.example.accountingproject.service;

import com.example.accountingproject.dto.CategoryDto;
import com.example.accountingproject.entity.Category;
import com.example.accountingproject.service.common.CrudService;

public interface CategoryService extends CrudService<CategoryDto, Long> {
    boolean isTheCategoryEmpty(Category category);
}
