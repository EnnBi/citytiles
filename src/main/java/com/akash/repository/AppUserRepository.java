package com.akash.repository;

import org.springframework.data.repository.CrudRepository;

import com.akash.entity.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Long>{

}
