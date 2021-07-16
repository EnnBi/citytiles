package com.akash.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.akash.entity.CustomerStatement;
import com.akash.entity.DayBook;
import com.akash.entity.DealerStatement;
import com.akash.entity.DriverStatement;
import com.akash.entity.LabourStatement;
import com.akash.entity.OwnerStatement;
import com.akash.repository.custom.DayBookCustomizedRepository;
import com.akash.util.Constants;

public interface DayBookRepository extends CrudRepository<DayBook, Long> ,DayBookCustomizedRepository{

	DayBook findByTransactionNumber(String chequeNumber);
	
	@Query("Select sum(d.amount) from DayBook d where d.date = :date and d.transactionType = :type")
	Double findTotalAmountByDateAndType(@Param("date") LocalDate date,@Param("type") String type);
	
	@Query("Select sum(d.amount) from DayBook d where d.transactionType = 'Revenue'")
	Double findTotalRevenue();
	
	@Query("Select sum(d.amount) from DayBook d where d.transactionType = 'Expenditure'")
	Double findTotalExpenditure();
	
	@Query("Select sum(d.amount) from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Expenditure'")
	Double findUserCredits(@Param("id") long id,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	
	@Query("Select sum(d.amount) from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Revenue'")
	Double findUserDebits(@Param("id") long id,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	
	@Query("Select new com.akash.entity.DriverStatement(d.transactionBy,d.amount,d.date,d.transactionNumber,'"+Constants.DAYBOOK+"') from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Expenditure'")
	List<DriverStatement> findDriverCreditsBetweenDates(@Param("id") long id,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	
	@Query("Select new com.akash.entity.DriverStatement(d.amount,d.transactionBy,d.date,d.transactionNumber,'"+Constants.DAYBOOK+"') from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Revenue'")
	List<DriverStatement> findDriverDebitsBetweenDates(@Param("id") long id,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);

	@Query("Select new com.akash.entity.DealerStatement(d.transactionBy,d.amount,d.date,d.transactionNumber,'"+Constants.DAYBOOK+"') from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Expenditure'")
	List<DealerStatement> findDealerCreditsBetweenDates(@Param("id") long id,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	
	@Query("Select new com.akash.entity.DealerStatement(d.amount,d.transactionBy,d.date,d.transactionNumber,'"+Constants.DAYBOOK+"') from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Revenue'")
	List<DealerStatement> findDealerDebitsBetweenDates(@Param("id") long id,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);

	
	@Query("Select new com.akash.entity.LabourStatement(d.transactionBy,d.amount,d.date,d.transactionNumber,'"+Constants.DAYBOOK+"') from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Expenditure'")
	List<LabourStatement> findLabourStatementCreditsBetweenDates(@Param("id") long id,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	
	@Query("Select new com.akash.entity.LabourStatement(d.amount,d.transactionBy,d.date,d.transactionNumber,'"+Constants.DAYBOOK+"') from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Revenue'")
	List<LabourStatement> findLabourStatementDebitsBetweenDates(@Param("id") long id,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);

	@Query("Select sum(d.amount) from DayBook d where d.accountNumber= :accNum and (d.date Between :startDate and :endDate) and d.transactionType = 'Expenditure'")
	Double findOwnerDebit(@Param("accNum") String accountNumber,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	
	@Query("Select sum(d.amount) from DayBook d where d.accountNumber = :accNum and (d.date Between :startDate and :endDate) and d.transactionType = 'Revenue'")
	Double findOwnerCredit(@Param("accNum") String accountNumber,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	
	@Query("Select new com.akash.entity.OwnerStatement(d.date,d.user.name,d.transactionNumber,d.transactionType,d.transactionBy,d.accountNumber,d.responsiblePerson,d.amount) from DayBook d where d.accountNumber = :accNum and (d.date Between :startDate and :endDate)")
	List<OwnerStatement> findByAccountNumberAndDateBetween(@Param("accNum") String accountNumber,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	
	@Query("Select new com.akash.entity.CustomerStatement(d.amount,d.transactionBy,d.date,d.transactionNumber) from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Revenue'")
	List<CustomerStatement> findCustomerDebitsBetweenDates(@Param("id") long id,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	
	@Query("Select new com.akash.entity.CustomerStatement(d.transactionBy,d.amount,d.date,d.transactionNumber) from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Expenditure'")
	List<CustomerStatement> findCustomerCreditsBetweenDates(@Param("id") long id,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
}
