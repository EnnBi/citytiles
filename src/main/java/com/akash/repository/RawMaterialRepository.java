package com.akash.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.akash.entity.DealerStatement;
import com.akash.entity.RawMaterial;
import com.akash.repository.custom.RawMaterialCustomizedRepository;
import com.akash.util.Constants;

public interface RawMaterialRepository extends CrudRepository<RawMaterial, Long>,RawMaterialCustomizedRepository {

	public boolean existsByChalanNumber(String Number);
	
	@Query("select r from RawMaterial r where r.chalanNumber=:chalanNum and r.id!=:id")
	public RawMaterial checkRawMaterialAlreadyExists(@Param("chalanNum") String chalanNumber,@Param("id") long id);
	
	@Query("Select Sum(r.amount) from RawMaterial r where r.dealer.id = :id and r.date between :startDate and :endDate")
	 Double sumDebits(@Param("id") long dealer,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	
	@Query("Select new com.akash.entity.DealerStatement(r.date,r.chalanNumber,r.material.name,r.quantity,r.unit,r.amount,'"+Constants.RAW_MATERIAL+"') from RawMaterial r where r.dealer.id = :id and r.date between :startDate and :endDate ")
	public List<DealerStatement> findDealerDebits(@Param("id") long dealer,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);

}
