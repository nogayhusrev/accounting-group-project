package com.example.accountingproject.service.impl;

import com.example.accountingproject.dto.ProductDto;
import com.example.accountingproject.entity.Company;
import com.example.accountingproject.entity.Product;
import com.example.accountingproject.mapper.MapperUtil;
import com.example.accountingproject.repository.ProductRepository;
import com.example.accountingproject.service.ProductService;
import com.example.accountingproject.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final MapperUtil mapperUtil;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, MapperUtil mapperUtil) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public ProductDto findById(Long productId) {
        return mapperUtil.convert(productRepository.findById(productId).get(), new ProductDto());
    }

    @Override
    public List<ProductDto> findAll() {
        Company company = mapperUtil.convert(userService.getCurrentUser().getCompany(), new Company());
        return productRepository.findAll().stream()
                .filter(product -> product.getCategory().getCompany().getTitle().equalsIgnoreCase(company.getTitle()))
                .sorted(Comparator.comparing((Product product) -> product.getCategory().getDescription())
                        .thenComparing(Product::getName))
                .map(product -> mapperUtil.convert(product, new ProductDto())).collect(Collectors.toList());
    }

    @Override
    public void save(ProductDto productDto) {
        Product product = mapperUtil.convert(productDto, new Product());
        product.setIsDeleted(false);
        product.setQuantityInStock(0);
        productRepository.save(product);

    }

    @Override
    public void delete(Long productId) {
        Product product = productRepository.findById(productId).get();

        if (product.getQuantityInStock() > 0)
            return;

        product.setIsDeleted(true);
        product.setName(product.getName() + "_" + product.getId() + "_DELETED");

        productRepository.save(product);
    }

    @Override
    public void update(ProductDto productDto, Long productId) {
        productDto.setId(productId);
        productRepository.save(mapperUtil.convert(productDto, new Product()));
    }

    @Override
    public boolean isExist(ProductDto productDto, Long productId) {
        Long idCheck = productRepository.findAll().stream()
                .filter(savedProduct -> savedProduct.getName().equalsIgnoreCase(productDto.getName()))
                .filter(savedProduct -> savedProduct.getId() != productId)
                .count();

        return idCheck > 0;
    }

    @Override
    public boolean isExist(ProductDto productDto) {
        return productRepository.findAll().stream()
                .filter(savedProduct -> savedProduct.getName().equalsIgnoreCase(productDto.getName()))
                .count() > 0;
    }
}
