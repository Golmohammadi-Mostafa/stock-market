package com.payconiq.stockmarket.service.impl;


import com.payconiq.stockmarket.domain.Role;
import com.payconiq.stockmarket.enums.RoleType;
import com.payconiq.stockmarket.repository.RoleRepository;
import com.payconiq.stockmarket.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByName(RoleType roleName) {
        return roleRepository.findByName(roleName);
    }
}
