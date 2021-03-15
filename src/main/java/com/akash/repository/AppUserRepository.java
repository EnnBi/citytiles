package com.akash.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.akash.entity.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Long>,PagingAndSortingRepository<AppUser, Long>{

}
