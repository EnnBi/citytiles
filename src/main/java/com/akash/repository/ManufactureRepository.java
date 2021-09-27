package com.akash.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.akash.entity.AppUser;
import com.akash.entity.LabourStatement;
import com.akash.entity.Manufacture;
import com.akash.projections.ChartProjection;
import com.akash.repository.custom.ManufactureCustomizedRepository;
import com.akash.util.Constants;

public interface ManufactureRepository extends CrudRepository<Manufacture,Long>,ManufactureCustomizedRepository {

	@Query("Select sum(m.quantity) as quantity,m.date as date,m.product.name as name from Manufacture m where m.date between :startDate and :endDate group by m.date,m.product")
	List<ChartProjection> findQuantityGroupByDateAndProductBetweenDates(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate); 

	@Query("Select sum(l.amountPerHead) from LabourInfo l where :labour MEMBER OF l.labours and l.manufacture.date between :startDate and :endDate ")
	Double sumManufactureDebits(@Param("labour") AppUser labour,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	
	@Query("Select new com.akash.entity.LabourStatement(l.manufacture.date,l.manufacture.product.name,l.manufacture.size.name,l.quantity,l.amountPerHead,'"+Constants.MANUFACTURE+"') from LabourInfo l where :labour MEMBER OF l.labours and l.manufacture.date between :startDate and :endDate ")
	List<LabourStatement> findLabourDebits(@Param("labour") AppUser labour,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);

	@Query("Select Sum(m.quantity) from  Manufacture m where m.product.id = :product and m.size.id = :size and (m.date between :startDate and :endDate)")
	Double findSumOfManufactured(@Param("product") long product,@Param("size") long size,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	
	
}
