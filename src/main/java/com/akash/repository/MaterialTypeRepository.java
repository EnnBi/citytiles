package com.akash.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.akash.entity.MaterialType;

public interface MaterialTypeRepository extends CrudRepository<MaterialType, Long>,PagingAndSortingRepository<MaterialType, Long> {

}
