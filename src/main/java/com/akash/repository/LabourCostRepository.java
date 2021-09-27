package com.akash.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.akash.entity.LabourCost;

public interface LabourCostRepository extends CrudRepository<LabourCost, Long>,PagingAndSortingRepository<LabourCost, Long>  {
	
		
}
