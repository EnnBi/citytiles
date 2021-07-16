package com.akash.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.akash.entity.UserType;

public interface UserTypeRepository extends CrudRepository<UserType, Long>,PagingAndSortingRepository<UserType, Long> {
	
	public boolean existsByName(String name);
	
	@Query("select u from UserType u where u.name=:name and u.id!=:id")
	public UserType chechUserAlreadyExists(@Param("name") String name,@Param("id") long id);
	
	UserType findByName(String name);
}
