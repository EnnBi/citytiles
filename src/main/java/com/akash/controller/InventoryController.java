package com.akash.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.akash.entity.InventoryCount;
import com.akash.entity.Product;
import com.akash.repository.BillBookRepository;
import com.akash.repository.ManufactureRepository;
import com.akash.repository.ProductRepository;
import com.akash.repository.SizeRepository;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
	@Autowired
	ProductRepository productRepo;
	@Autowired
	BillBookRepository billBookRepo;
	@Autowired
	ManufactureRepository manufactureRepo;
	@Autowired
	SizeRepository sizeRepo;
	
	@RequestMapping(method=RequestMethod.GET)
	public String inventoryGet(Model model){
		model.addAttribute("products",productRepo.findAll());
		model.addAttribute("inventorySearch", new InventorySearch());
		return "inventory";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String doPost(InventorySearch is,Model model){
		
		 Product product = productRepo.findById(is.getProduct()).orElse(null);
		 List<InventoryCount> counts = new ArrayList<InventoryCount>();   
		 product.getSizes().stream().map(p->p.getId()).forEach(id->{
				Double manufactured =  manufactureRepo.findSumOfManufactured(is.getProduct(),id, is.getStartDate(), is.getEndDate());
				Double sold = billBookRepo.findSumOfSold(is.getProduct(), id, is.getStartDate(), is.getEndDate());
				String sizeName = sizeRepo.findById(id).get().getName();
				String productName = product.getName();
				InventoryCount inventoryCount = new InventoryCount(productName,sizeName,manufactured,sold);
				counts.add(inventoryCount);
		 });
		 model.addAttribute("counts", counts);
		 model.addAttribute("inventorySearch",is);
		 model.addAttribute("products",productRepo.findAll());
		return "inventory";
	}
}
