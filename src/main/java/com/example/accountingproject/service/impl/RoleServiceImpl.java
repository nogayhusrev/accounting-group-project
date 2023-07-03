package com.example.accountingproject.service.impl;

import com.example.accountingproject.dto.RoleDto;
import com.example.accountingproject.entity.Role;
import com.example.accountingproject.mapper.MapperUtil;
import com.example.accountingproject.repository.RoleRepository;
import com.example.accountingproject.service.RoleService;
import com.example.accountingproject.service.SecurityService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;

    private final UserService userService;

    private final SecurityService securityService;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil, UserService userService, @Lazy SecurityService securityService) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
        this.securityService = securityService;
    }

    @Override
    public RoleDto findById(Long roleId) {
        return mapperUtil.convert(roleRepository.findRoleById(roleId), new RoleDto());
    }

    @Override
    public List<RoleDto> getRolesForCurrentUser() {

        List<Role> roles;

        if (userService.getCurrentUser().getRole().getDescription().equals("Root User")) {
            roles = roleRepository.findAll().stream()
                    .filter(role -> role.getDescription().equals("Admin")).collect(Collectors.toList());
        } else {
            roles = roleRepository.findAll().stream()
                    .filter(role -> !role.getDescription().equals("Root User"))
                    .collect(Collectors.toList());
        }

        return roles.stream().map(role -> mapperUtil.convert(role, new RoleDto())).collect(Collectors.toList());
    }


    @Override
    public List<RoleDto> findAll() {
        throw new IllegalStateException("Not Implemented");
    }

    @Override
    public void save(RoleDto roleDto) {
        throw new IllegalStateException("Not Implemented");

    }

    @Override
    public void delete(Long roleId) {
        throw new IllegalStateException("Not Implemented");

    }

    @Override
    public void update(RoleDto roleDto, Long roleId) {
        throw new IllegalStateException("Not Implemented");

    }

    @Override
    public boolean isExist(RoleDto roleDto) {
        throw new IllegalStateException("Not Implemented");

    }
}
