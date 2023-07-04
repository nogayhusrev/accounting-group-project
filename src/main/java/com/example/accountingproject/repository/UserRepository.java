package com.example.accountingproject.repository;

import com.example.accountingproject.entity.Company;
import com.example.accountingproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);
    User findUserById(Long id);
    List<User> findAllByRole_Description(String role);
    //List<User> findAllByRole_DescriptionContainingOrderByCompany_TitleAscRole_DescriptionAsc(String role);
    //List<User> findAllCompanyContainingOrderByCompany_TitleAscRole_DescriptionAsc(String role);
    List<User> findAllByRole(String role);
    List<User> findAllByCompany_Title(String companyName);
    List<User> findAllByCompany(Company company);
}
