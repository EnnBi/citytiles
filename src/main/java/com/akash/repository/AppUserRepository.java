package com.akash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.akash.entity.AppUser;
import com.akash.entity.Site;
import com.akash.projections.AppUserProjection;
import com.akash.repository.custom.AppUserCustomizedRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long>,AppUserCustomizedRepository {
	
	public boolean existsByContactOrAccountNumber(String contact,String accountNumber);
	
	@Query("select a from AppUser a where(a.contact=:contact and a.accountNumber=:accNum) and a.id!=:id")
	public AppUser chechUserExistsAlready(@Param("contact") String Contact,@Param("accNum") String accNum,@Param("id") long id);
	
	public List<AppUser> findByUserType_Name(String userType);
	
	@Query("Select a from AppUser a where a.userType.name in :userType")
	public List<AppUser> findAppUsersOnType(@Param("userType") String[] userType);
	
	
	public List<AppUserProjection> findByUserType_NameIn(String [] userTypes);

	@Query("Select u.sites from AppUser u where u.id= :id")
	List<Site> findSitesOnUserId(@Param("id") long id);
	
	
}
 