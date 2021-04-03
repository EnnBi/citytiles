package com.akash.repository.custom;

import java.util.List;

import com.akash.entity.AppUser;
import com.akash.entity.AppUserSearch;

public interface AppUserCustomizedRepository {
	
List<AppUser> searchAppUserPaginated(AppUserSearch appUserSearch,int from);
	
	
	public long searchAppUsersCount(AppUserSearch appUserSearch); 

}
