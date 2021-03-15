package com.akash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import com.akash.entity.RawMaterial;
import com.akash.repository.RawMaterialRepository;

@Controller
@RequestMapping("/raw-material")
public class RawMaterialController {
	@Autowired
	RawMaterialRepository rawMaterialRepository;
	@GetMapping("/")
	public String add(Model model) {
		model.addAttribute("rawMaterial", new RawMaterial());
		return null;
	}

	@GetMapping("/save")
	public String save(@ModelAttribute("rawMaterial") RawMaterial rawMaterial, Model model) {
		rawMaterialRepository.save(rawMaterial);
		return null;
	}

	@GetMapping("/update")
	public String update(@PathVariable("Id") long id, Model model) {

		model.addAttribute("rawMaterial", rawMaterialRepository.findById(id));

		return null;
	}

	@GetMapping("/delete")
	public String delete(@PathVariable("id") long id) {
		rawMaterialRepository.deleteById(id);
		return null;
	}
	@GetMapping("/list")
	public String list(Model model)
	{
		model.addAttribute("list",rawMaterialRepository.findAll());
		return null;
	}

}
