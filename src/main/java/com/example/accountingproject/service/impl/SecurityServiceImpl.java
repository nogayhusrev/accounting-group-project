package com.example.accountingproject.service.impl;


import com.example.accountingproject.dto.UserDto;
import com.example.accountingproject.entity.User;
import com.example.accountingproject.entity.common.UserPrincipal;
import com.example.accountingproject.repository.UserRepository;
import com.example.accountingproject.service.SecurityService;
import com.example.accountingproject.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SecurityServiceImpl implements SecurityService {
    private final UserRepository userRepository;
    private final UserService userService;

    public SecurityServiceImpl(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException(username);

        return new UserPrincipal(user);

    }

    @Override
    public UserDto getCurrentUser() {
        return userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
