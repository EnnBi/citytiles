package com.akash.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.akash.entity.Size;

public interface SizeRepository extends CrudRepository<Size, Long>,PagingAndSortingRepository<Size, Long> {

}
