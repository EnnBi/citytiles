package com.akash.repository;

import org.springframework.data.repository.CrudRepository;

import com.akash.entity.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {

}
