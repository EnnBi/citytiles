package com.akash.repository.custom;

import java.util.List;

import com.akash.entity.BillBookSearch;
import com.akash.entity.dto.BillBookDTO;

public interface BillBookCustomizedRepository {
	List<BillBookDTO> searchPaginated(BillBookSearch billBookSearch,int from);
	long count(BillBookSearch billBookSearch);
}
