package com.uber.auth.service;

import com.uber.entity.user.Role;
import com.uber.entity.user.User;
import com.uber.repository.user.RoleJpaRepository;
import com.uber.repository.user.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private RoleJpaRepository roleJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role adminRole = Role.builder().roleName("Admin").roleDescription("Admin role").build();
        roleJpaRepository.save(adminRole);

        Role userRole = Role.builder().roleName("User").roleDescription("Default role for new users").build();
        roleJpaRepository.save(userRole);

        User adminUser = new User();
        adminUser.setUsername("admin123");
        adminUser.setPassword(getEncodedPassword("admin@pass"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userJpaRepository.save(adminUser);

    }

    public User registerAsUser(User user) {
        Role role = roleJpaRepository.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setPassword(getEncodedPassword(user.getPassword()));

        return userJpaRepository.save(user);
    }


    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
