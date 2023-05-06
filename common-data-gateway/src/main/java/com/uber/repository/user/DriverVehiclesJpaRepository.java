package com.uber.repository.user;

import com.uber.entity.vehicle.DriverVehicles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverVehiclesJpaRepository extends JpaRepository<DriverVehicles, String> {
    DriverVehicles findByDriverId(int driverId);
}