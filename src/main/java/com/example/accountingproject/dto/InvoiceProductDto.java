package com.example.accountingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProductDto {

    private Long id;

    @NotNull(message = "Quantity is a required field.")
    @Range(min = 1, message = "Maximum order count is 100")
    private int quantity;

    @NotNull(message = "Price is a required field.")
    @Range(min = 1, message = "Price should be at least $1")
    private BigDecimal price;

    @NotNull(message = "Tax is a required field.")
    @Range(min = 0, max = 20, message = "Tax should be between 5% and 20%")
    private int tax;

    private BigDecimal total;
    private BigDecimal profitLoss;
    private int remainingQuantity;

    private InvoiceDto invoice;

    @NotNull(message = "Product is a required field.")
    private ProductDto product;

}
