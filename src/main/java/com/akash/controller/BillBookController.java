package com.akash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akash.entity.BillBook;
import com.akash.repository.BillBookRepository;

@Controller
@RequestMapping("/bill-book")
public class BillBookController {
	@Autowired
	BillBookRepository billBookRepository;

	@GetMapping("/")
	public String add(Model model) {
		model.addAttribute("billBook", new BillBook());
		return null;
	}

	@GetMapping("/save")
	public String save(@ModelAttribute("billBook") BillBook billBook, Model model) {
		billBookRepository.save(billBook);
		return null;
	}

	@GetMapping("/update")
	public String update(@PathVariable("Id") long id, Model model) {

		model.addAttribute("billBook", billBookRepository.findById(id));

		return null;
	}

	@GetMapping("/delete")
	public String delete(@PathVariable("id") long id) {
		billBookRepository.deleteById(id);
		return null;
	}
	@GetMapping("/list")
	public String list(Model model)
	{
		model.addAttribute("list",billBookRepository.findAll());
		return null;
	}

}
