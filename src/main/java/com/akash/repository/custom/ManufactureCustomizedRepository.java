package com.akash.repository.custom;

import java.util.List;

import com.akash.entity.ManufactureSearch;
import com.akash.entity.dto.ManufactureDTO;

public interface ManufactureCustomizedRepository {
	List<ManufactureDTO> searchPaginated(ManufactureSearch manufactureSearch,int from);
	long count(ManufactureSearch manufactureSearch);
}
