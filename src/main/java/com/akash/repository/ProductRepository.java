package com.akash.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.akash.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long>,PagingAndSortingRepository<Product, Long> {
	
	public boolean existsByName(String name);
	
	@Query("select p from Product p where p.name=:name and p.id!=:id")
	public Product checkProductAlreadyExists(@Param("name") String name,@Param("id") long id);

}
