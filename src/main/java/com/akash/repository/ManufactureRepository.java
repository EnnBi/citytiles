package com.akash.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.akash.entity.Manufacture;
import com.akash.projections.ChartProjection;
import com.akash.repository.custom.ManufactureCustomizedRepository;

public interface ManufactureRepository extends CrudRepository<Manufacture,Long>,ManufactureCustomizedRepository {

	@Query("Select sum(m.totalQuantity) as quantity,m.date as date,m.product.name as name from Manufacture m where m.date between :startDate and :endDate group by m.date,m.product")
	List<ChartProjection> findQuantityGroupByDateAndProductBetweenDates(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate); 
}
