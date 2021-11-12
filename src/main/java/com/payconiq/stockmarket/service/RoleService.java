package com.payconiq.stockmarket.service;


import com.payconiq.stockmarket.domain.Role;
import com.payconiq.stockmarket.enums.RoleType;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(RoleType roleName);
}
