package com.akash.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.akash.entity.DayBook;
import com.akash.repository.custom.DayBookCustomizedRepository;

public interface DayBookRepository extends CrudRepository<DayBook, Long> ,DayBookCustomizedRepository{

	DayBook findByTransactionNumber(String chequeNumber);
	
	@Query("Select sum(d.amount) from DayBook d where d.date = :date and d.transactionType = :type")
	Double findTotalAmountByDateAndType(@Param("date") LocalDate date,@Param("type") String type);
	
	@Query("Select sum(d.amount) from DayBook d where d.transactionType = 'Revenue'")
	Double findTotalRevenue();
	
	@Query("Select sum(d.amount) from DayBook d where d.transactionType = 'Expenditure'")
	Double findTotalExpenditure();
}
