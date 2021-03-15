package com.akash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akash.entity.Product;
import com.akash.repository.ProductRepository;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductRepository productRepository;
	@GetMapping("/")
	public String add(Model model)
	{
		model.addAttribute("product", new Product());
		model.addAttribute("list", productRepository.findAll());
		return null;
				
	}
	@GetMapping("/save")
	public String save(@ModelAttribute("product") Product product,Model model)
	{
		productRepository.save(product);
		return null;
	}
	@GetMapping("/update")
	public String update(@PathVariable("id") long id,Model model)
	{
		model.addAttribute("product",productRepository.findById(id));
		return null;
	}
	@GetMapping("/delete")
	public String delete(@PathVariable("id") long id)
	{
		productRepository.deleteById(id);
		return null;
	}
}
