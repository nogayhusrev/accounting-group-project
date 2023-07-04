package com.example.accountingproject.service.impl;

import com.example.accountingproject.dto.ProductDto;
import com.example.accountingproject.mapper.MapperUtil;
import com.example.accountingproject.repository.ProductRepository;
import com.example.accountingproject.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public ProductDto findById(Long productId) {
        return mapperUtil.convert(productRepository.findById(productId).get(), new ProductDto());
    }

    @Override
    public List<ProductDto> findAll() {
        return null;
    }

    @Override
    public void save(ProductDto productDto) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void update(ProductDto productDto, Long aLong) {

    }

    @Override
    public boolean isExist(ProductDto productDto) {
        return false;
    }

    @Override
    public boolean isExist(ProductDto productDto, Long aLong) {
        return false;
    }
}
