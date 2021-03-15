package com.akash.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.akash.entity.Site;

public interface SiteRepository extends CrudRepository<Site, Long>,PagingAndSortingRepository<Site,Long> {

}
