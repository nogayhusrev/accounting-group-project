package com.example.accountingproject.service.impl;

import com.example.accountingproject.dto.InvoiceProductDto;
import com.example.accountingproject.dto.ProductDto;
import com.example.accountingproject.entity.Company;
import com.example.accountingproject.entity.Invoice;
import com.example.accountingproject.entity.InvoiceProduct;
import com.example.accountingproject.entity.Product;
import com.example.accountingproject.enums.InvoiceType;
import com.example.accountingproject.mapper.MapperUtil;
import com.example.accountingproject.repository.InvoiceProductRepository;
import com.example.accountingproject.service.InvoiceProductService;
import com.example.accountingproject.service.InvoiceService;
import com.example.accountingproject.service.ProductService;
import com.example.accountingproject.service.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;
    private final UserService userService;
    private final InvoiceService invoiceService;
    private final ProductService productService;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil, UserService userService, InvoiceService invoiceService, ProductService productService) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
        this.invoiceService = invoiceService;
        this.productService = productService;
    }


    @Override
    public List<InvoiceProductDto> findInvoiceProductsByInvoiceId(Long invoiceId) {
        return invoiceProductRepository.findInvoiceProductsByInvoiceId(invoiceId).stream()
                .sorted(Comparator.comparing((InvoiceProduct each) -> each.getInvoice().getInvoiceNo()).reversed())
                .map(each -> {
                    InvoiceProductDto dto = mapperUtil.convert(each, new InvoiceProductDto());
                    dto.setTotal(each.getPrice().multiply(BigDecimal.valueOf(each.getQuantity() * (each.getTax()+100)/100d)));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceProduct> findInvoiceProductsByInvoiceType(InvoiceType invoiceType) {
        Company company = mapperUtil.convert(userService.getCurrentUser().getCompany(), new Company());
        return invoiceProductRepository.findAll().stream()
                .filter(invoiceProduct -> invoiceProduct.getInvoice().getCompany().getTitle().equals(company.getTitle()))
                .filter(invoiceProduct -> invoiceProduct.getInvoice().getInvoiceType().equals(invoiceType))
                .collect(Collectors.toList());
    }


    @Override
    public void saveInvoiceProductByInvoiceId(InvoiceProductDto invoiceProductDto, Long invoiceId) {
        Invoice invoice = mapperUtil.convert(invoiceService.findById(invoiceId), new Invoice());
        InvoiceProduct invoiceProduct = mapperUtil.convert(invoiceProductDto, new InvoiceProduct());
        invoiceProduct.setInvoice(invoice);
        invoiceProduct.setProfitLoss(BigDecimal.ZERO);
        save(mapperUtil.convert(invoiceProduct, new InvoiceProductDto()));
    }

    @Override
    public void completeApprovalProcedures(Long invoiceId, InvoiceType type) {
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoice_Id(invoiceId);
        if (type == InvoiceType.SALES) {
            for (InvoiceProduct salesInvoiceProduct : invoiceProductList) {
                if (checkProductQuantity(mapperUtil.convert(salesInvoiceProduct, new InvoiceProductDto()))) {
                    updateQuantityOfProduct(salesInvoiceProduct, type);
                    salesInvoiceProduct.setRemainingQuantity(salesInvoiceProduct.getQuantity());
                    invoiceProductRepository.save(salesInvoiceProduct);
                    setProfitLossOfInvoiceProductsForSalesInvoice(salesInvoiceProduct);
                } else {
                    throw new NoSuchElementException("This sale cannot be completed due to insufficient quantity of the product"); 
                }
            }
        } else {
            for (InvoiceProduct purchaseInvoiceProduct : invoiceProductList) {
                updateQuantityOfProduct(purchaseInvoiceProduct, type);
                purchaseInvoiceProduct.setRemainingQuantity(purchaseInvoiceProduct.getQuantity());
                invoiceProductRepository.save(purchaseInvoiceProduct);
            }
        }
    }

    @Override
    public boolean checkProductQuantity(InvoiceProductDto salesInvoiceProduct) {
        return salesInvoiceProduct.getProduct().getQuantityInStock() >= salesInvoiceProduct.getQuantity();

    }

    @Override
    public List<InvoiceProduct> findInvoiceProductsByInvoiceTypeAndProductRemainingQuantity(InvoiceType type, Product product, Integer remainingQuantity) {
        return invoiceProductRepository.findInvoiceProductsByInvoiceInvoiceTypeAndProductAndRemainingQuantityNotOrderByIdAsc(type, product, remainingQuantity);
    }


    @Override
    public List<InvoiceProduct> findAllInvoiceProductsByProductId(Long id) {
        return invoiceProductRepository.findAllInvoiceProductByProductId(id);
    }

    @Override
    public InvoiceProductDto findById(Long invoiceProductId) {
        return mapperUtil.convert(invoiceProductRepository.findById(invoiceProductId), new InvoiceProductDto());
    }

    @Override
    public List<InvoiceProductDto> findAll() {
        Company company = mapperUtil.convert(userService.getCurrentUser().getCompany(), new Company());
        return invoiceProductRepository.findAll().stream()
                .filter(invoiceProduct -> invoiceProduct.getInvoice().getCompany().getTitle().equals(company.getTitle()))
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(InvoiceProductDto invoiceProductDto) {
        invoiceProductRepository.save(mapperUtil.convert(invoiceProductDto, new InvoiceProduct()));
    }

    public void delete(Long invoiceProductId) {
        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(invoiceProductId).get();
        invoiceProduct.setIsDeleted(true);
        invoiceProductRepository.save(invoiceProduct);
    }

    @Override
    public void update(InvoiceProductDto invoiceProductDto, Long aLong) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public boolean isExist(InvoiceProductDto invoiceProductDto, Long invoiceProductId) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public boolean isExist(InvoiceProductDto invoiceProductDto) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }


    private void updateQuantityOfProduct(InvoiceProduct invoiceProduct, InvoiceType type) {
        ProductDto productDto = mapperUtil.convert(invoiceProduct.getProduct(), new ProductDto());
        if (type.equals(InvoiceType.SALES)) {
            productDto.setQuantityInStock(productDto.getQuantityInStock() - invoiceProduct.getQuantity());
        } else {
            productDto.setQuantityInStock(productDto.getQuantityInStock() + invoiceProduct.getQuantity());
        }
        productService.update(productDto, productDto.getId());
    }

    private void setProfitLossOfInvoiceProductsForSalesInvoice(InvoiceProduct salesInvoiceProduct) {
        List<InvoiceProduct> availableProductsForSale = findInvoiceProductsByInvoiceTypeAndProductRemainingQuantity(InvoiceType.PURCHASE, salesInvoiceProduct.getProduct(), 0);
        for (InvoiceProduct availableProduct : availableProductsForSale) {
            if (salesInvoiceProduct.getRemainingQuantity() <= availableProduct.getRemainingQuantity()) {
                BigDecimal costTotalForQty = availableProduct.getPrice().multiply(
                        BigDecimal.valueOf(salesInvoiceProduct.getRemainingQuantity() * (availableProduct.getTax() +100)/100d));
                BigDecimal salesTotalForQty = salesInvoiceProduct.getPrice().multiply(
                        BigDecimal.valueOf(salesInvoiceProduct.getRemainingQuantity() * (salesInvoiceProduct.getTax() +100)/100d));
                BigDecimal profitLoss = salesInvoiceProduct.getProfitLoss().add(salesTotalForQty.subtract( costTotalForQty));
                availableProduct.setRemainingQuantity(availableProduct.getRemainingQuantity() - salesInvoiceProduct.getRemainingQuantity());
                salesInvoiceProduct.setRemainingQuantity(0);
                salesInvoiceProduct.setProfitLoss(profitLoss);
                invoiceProductRepository.save(availableProduct);
                invoiceProductRepository.save(salesInvoiceProduct);
                break;
            } else {
                BigDecimal costTotalForQty = availableProduct.getPrice()
                        .multiply(BigDecimal.valueOf(availableProduct.getRemainingQuantity() * (availableProduct.getTax() +100)/100d));
                BigDecimal salesTotalForQty = salesInvoiceProduct.getPrice().multiply(
                        BigDecimal.valueOf(availableProduct.getRemainingQuantity() * (salesInvoiceProduct.getTax() +100)/100d));
                BigDecimal profitLoss = salesInvoiceProduct.getProfitLoss()
                        .add(salesTotalForQty.subtract(costTotalForQty));
                salesInvoiceProduct.setRemainingQuantity(salesInvoiceProduct.getRemainingQuantity() - availableProduct.getRemainingQuantity());
                availableProduct.setRemainingQuantity(0);
                salesInvoiceProduct.setProfitLoss(profitLoss);
                invoiceProductRepository.save(availableProduct);
                invoiceProductRepository.save(salesInvoiceProduct);
            }
        }
    }

}