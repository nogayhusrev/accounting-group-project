package com.example.accountingproject.service;

import com.example.accountingproject.dto.ProductDto;
import com.example.accountingproject.service.common.CrudService;
import org.springframework.stereotype.Service;


public interface ProductService extends CrudService<ProductDto,Long> {
}
