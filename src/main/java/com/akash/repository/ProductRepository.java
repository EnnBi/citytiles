package com.akash.repository;

import org.springframework.data.repository.CrudRepository;

import com.akash.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
