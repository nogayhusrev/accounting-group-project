package com.example.accountingproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "categories")
public class Category extends BaseEntity{

    private String description;
    private Company company;

}
