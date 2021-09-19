package com.akash.controller;

import java.time.LocalDate;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.akash.repository.BillBookRepository;
import com.akash.repository.DayBookRepository;
import com.akash.repository.ManufactureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

	@Autowired
	DayBookRepository dayBookRepository;
	
	@Autowired
	ManufactureRepository manufactureRepository;
	
	@Autowired
	BillBookRepository billBookRepository;
	
	@GetMapping("/dashboard")
	public String home(){
		Log.info("Good");
		return "index";
	}
	
	@GetMapping("/template")
	public String template(){
		return "template";    
	}
	
	@GetMapping("/bar-chart")
	public ResponseEntity<?> barChart(){
		LocalDate startDate = LocalDate.now().minusDays(7);
		LocalDate endDate = LocalDate.now();
		return ResponseEntity.ok(manufactureRepository.findQuantityGroupByDateAndProductBetweenDates(startDate, endDate));
	}
}
