package com.akash.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.akash.entity.UserType;

public interface UserTypeRepository extends CrudRepository<UserType, Long>,PagingAndSortingRepository<UserType, Long> {
}
