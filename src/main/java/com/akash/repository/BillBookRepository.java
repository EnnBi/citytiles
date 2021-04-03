package com.akash.repository;

import org.springframework.data.repository.CrudRepository;

import com.akash.entity.BillBook;
import com.akash.repository.custom.BillBookCustomizedRepository;

public interface BillBookRepository extends CrudRepository<BillBook,Long>,BillBookCustomizedRepository{
	
	boolean existsByReceiptNumber(String number);
}
