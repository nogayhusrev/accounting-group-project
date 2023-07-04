package com.example.accountingproject.service;

import com.example.accountingproject.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityService extends UserDetailsService {


    UserDto getCurrentUser();
}
