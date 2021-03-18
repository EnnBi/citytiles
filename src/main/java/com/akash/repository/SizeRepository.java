package com.akash.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.akash.entity.Size;

public interface SizeRepository extends CrudRepository<Size, Long>,PagingAndSortingRepository<Size, Long> {
	
	public boolean existsByName(String name);
	
	@Query("select s from Size s where s.name=:name and s.id!=:id")
	public Size checkSizeAlreadyExists(@Param("name") String name,@Param("id") long id);

}
