package com.uber.repository.user;

import com.uber.entity.user.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleJpaRepository extends JpaRepository<Vehicle, String> {
}