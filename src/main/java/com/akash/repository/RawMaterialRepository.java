package com.akash.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.akash.entity.AppUser;
import com.akash.entity.MaterialType;
import com.akash.entity.RawMaterial;

public interface RawMaterialRepository extends CrudRepository<RawMaterial, Long>,PagingAndSortingRepository<RawMaterial, Long>{
	
	

	public boolean existsByChalanNumber(String Number);

}
