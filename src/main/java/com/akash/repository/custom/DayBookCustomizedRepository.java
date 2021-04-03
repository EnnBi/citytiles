package com.akash.repository.custom;

import java.util.List;

import com.akash.entity.DayBook;
import com.akash.entity.DayBookSearch;

public interface DayBookCustomizedRepository {
	List<DayBook> searchPaginated(DayBookSearch dayBookSearch,int from);
	long count(DayBookSearch dayBookSearch);
}
