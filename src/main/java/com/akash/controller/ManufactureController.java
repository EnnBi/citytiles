package com.akash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akash.entity.Manufacture;
import com.akash.repository.ManufactureRepository;

@Controller
@RequestMapping("/manufacture")
public class ManufactureController {
	@Autowired
	ManufactureRepository manufactureRepository;
	@GetMapping("/")
	public String add(Model model)
	{
		model.addAttribute("manufacture", new Manufacture());
		return null;
	}
	
	@GetMapping("/save")
	public String save(@ModelAttribute("manufacture") Manufacture manufacture,Model model)
	{
	 manufactureRepository.save(manufacture);
	 return null;
	}

	@GetMapping("/update")
	public String update(@PathVariable("id") long id,Model model)
	{
		model.addAttribute("manufacture",manufactureRepository.findById(id));
		return null;
	}
	@GetMapping("/delete")
	public String delete(@PathVariable("id") long id)
	{
		manufactureRepository.deleteById(id);
		return null;
	}
	@GetMapping("/list")
	public String list(Model model)
	{
		model.addAttribute("list",manufactureRepository.findAll());
		return null;
	}
}
