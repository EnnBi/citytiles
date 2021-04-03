package com.akash.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.akash.entity.RawMaterial;
import com.akash.repository.custom.RawMaterialCustomizedRepository;

public interface RawMaterialRepository extends CrudRepository<RawMaterial, Long>,RawMaterialCustomizedRepository {
	
	

	public boolean existsByChalanNumber(String Number);
	
	@Query("select r from RawMaterial r where r.chalanNumber=:chalanNum and r.id!=:id")
	public RawMaterial checkRawMaterialAlreadyExists(@Param("chalanNum") String chalanNumber,@Param("id") long id);

}
