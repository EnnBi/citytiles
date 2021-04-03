package com.akash.projections;

import java.time.LocalDate;

public interface ChartProjection {

	int getQuantity();
	
	String getName();
	
	LocalDate getDate();
}
