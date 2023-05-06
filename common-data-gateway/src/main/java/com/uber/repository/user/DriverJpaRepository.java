package com.uber.repository.user;

import com.uber.entity.user.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverJpaRepository extends JpaRepository<Driver, String> {
    Optional<Driver> findByMobile(String mobile);
}