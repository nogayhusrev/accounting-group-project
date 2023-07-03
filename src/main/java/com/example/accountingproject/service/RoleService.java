package com.example.accountingproject.service;


import com.example.accountingproject.dto.RoleDto;
import com.example.accountingproject.service.common.CrudService;

import java.util.List;

public interface RoleService extends CrudService<RoleDto, Long> {

    List<RoleDto> getRolesForCurrentUser();
}
