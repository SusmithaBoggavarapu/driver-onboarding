package com.uber.service;

import com.uber.entity.user.Role;
import com.uber.repository.user.RoleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleJpaRepository repository;

    public Role createNewRole(Role role) {
        return repository.save(role);
    }
}
