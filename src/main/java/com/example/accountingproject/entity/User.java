package com.example.accountingproject.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    //    - String username / must be unique
    @Column(unique = true)
    private String username;
    //    - String password
    private String password;
    //    - String firstName
    private String firstName;
    //    - String lastName
    private String lastName;
    // String phone
    private String phone;
    //boolead enabled
    private boolean enabled;
    //    - Role role / many-to-one / will be seen under "role_id" column on the "users" table
    @ManyToOne
    @Column(name = "role_id")
    private Role role;
    //    - Company company / many-to-one / will be seen under "company_id" column on the "users" table
    @ManyToOne
    @Column(name = "company_id")
    private Company company;

}
