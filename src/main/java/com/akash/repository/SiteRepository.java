package com.akash.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.akash.entity.Site;

public interface SiteRepository extends CrudRepository<Site, Long>,PagingAndSortingRepository<Site,Long> {

	public boolean existsByName(String name);
	@Query("select s from Site s where s.name=:name and s.id!=:id")
	public Site checkSiteAlreadyExists(@Param("name") String name,@Param("id") long id);
}
