package com.akash.repository.custom;

import java.util.List;

import com.akash.entity.RawMaterial;
import com.akash.entity.RawMaterialSearch;

public interface RawMaterialCustomizedRepository {
	List<RawMaterial> searchRawMaterialPaginated(RawMaterialSearch rawMaterialSearch,int from);
	
	
	public long searchRawMaterialsCount(RawMaterialSearch rawMaterialSearch); 

}
