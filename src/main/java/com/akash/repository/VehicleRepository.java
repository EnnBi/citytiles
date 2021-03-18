package com.akash.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.akash.entity.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, Long>,PagingAndSortingRepository<Vehicle, Long> {

	public boolean existsByNumber(String number);
	
	@Query("select v from Vehicle v where v.number=:num and v.id!=:id")
	public Vehicle checkVehicleAlreadyExists(@Param("num") String number,@Param("id") long id);

}
