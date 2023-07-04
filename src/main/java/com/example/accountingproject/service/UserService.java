package com.example.accountingproject.service;

import com.example.accountingproject.dto.UserDto;
import com.example.accountingproject.service.common.CrudService;

public interface UserService extends CrudService<UserDto, Long> {
    UserDto findByUsername(String name);
    UserDto getCurrentUser();

}

