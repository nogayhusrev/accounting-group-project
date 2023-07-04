package com.example.accountingproject.converter;

import com.example.accountingproject.dto.ProductDto;
import com.example.accountingproject.service.ProductService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class ProductDtoConverter implements Converter<String, ProductDto> {

    private final ProductService productService;

    public ProductDtoConverter(@Lazy ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ProductDto convert(String id) {
        if (id == null || id.isBlank())
            return null;
        return productService.findById(Long.parseLong(id));    }
}
