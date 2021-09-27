package com.akash.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.akash.entity.Color;

public interface ColorRepository extends CrudRepository<Color, Long> {

	public boolean existsByName(String name);
	
	@Query("select c from Color c where c.name=:name and c.id!=:id")
	public Color checkColorAlreadyExists(@Param("name") String name,@Param("id") long id);
}
