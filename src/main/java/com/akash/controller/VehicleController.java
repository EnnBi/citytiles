package com.akash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import com.akash.entity.Vehicle;
import com.akash.repository.VehicleRepository;

@Controller
@RequestMapping("/vehicle")
public class VehicleController {
	@Autowired
	VehicleRepository vehicleRepository;
	
	@GetMapping("/")
	public String add(Model model)
	{
		model.addAttribute("vehicle", new Vehicle());
		model.addAttribute("list", vehicleRepository.findAll());
		return null;
				
	}
	@GetMapping("/save")
	public String save(@ModelAttribute("vehicle") Vehicle vehicle,Model model)
	{
		vehicleRepository.save(vehicle);
		return null;
	}
	@GetMapping("/update")
	public String update(@PathVariable("id") long id,Model model)
	{
		model.addAttribute("vehicle",vehicleRepository.findById(id));
		return null;
	}
	@GetMapping("/delete")
	public String delete(@PathVariable("id") long id)
	{
		vehicleRepository.deleteById(id);
		return null;
	}


}
