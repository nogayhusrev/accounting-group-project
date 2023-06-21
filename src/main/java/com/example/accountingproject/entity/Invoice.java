package com.example.accountingproject.entity;


import com.example.accountingproject.enums.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
@Where(clause = "is_deleted=false")
public class Invoice extends BaseEntity{

    private String invoiceNo;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;


    private LocalDate date;


    @ManyToOne
    @JoinColumn(name = "client_vendor_id")
    private ClientVendor clientVendor;


    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
