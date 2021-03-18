package com.akash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.akash.entity.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Long>,PagingAndSortingRepository<AppUser, Long>{
	
	
	public boolean existsByContactOrAccountNumber(String contact,String accountNumber);
	
	@Query("select a from AppUser a where(a.contact=:contact and a.accountNumber=:accNum) and a.id!=:id")
	public AppUser chechUserExistsAlready(@Param("contact") String Contact,@Param("accNum") String accNum,@Param("id") long id);
	
	public List<AppUser> findByUserType_Name(String userType);

}
