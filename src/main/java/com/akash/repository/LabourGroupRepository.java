package com.akash.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.akash.entity.LabourGroup; 

public interface LabourGroupRepository extends CrudRepository<LabourGroup, Long>,PagingAndSortingRepository<LabourGroup,Long> {

	boolean existsByName(String name);

	@Query("select l from LabourGroup l where l.name=:name and l.id!=:id")
	LabourGroup checkLabourGroupAlreadyExists(@Param("name") String name,@Param("id") long id);

}
