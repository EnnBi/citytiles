package com.akash.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akash.entity.Manufacture;
import com.akash.entity.ManufactureSearch;
import com.akash.entity.dto.ManufactureDTO;
import com.akash.repository.AppUserRepository;
import com.akash.repository.ManufactureRepository;
import com.akash.repository.ProductRepository;
import com.akash.repository.SizeRepository;
import com.akash.util.Constants;

@Controller
@RequestMapping("/manufacture")
public class ManufactureController {
	
	@Autowired
	ManufactureRepository manufactureRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	AppUserRepository appUserRepository;
	@Autowired
	SizeRepository sizeRepository;
	
	int from = 0;
	int total = 0;
	Long records = 0L;
	
	@GetMapping
	public String add(Model model)
	{
		model.addAttribute("manufacture", new Manufacture());
		model.addAttribute("products", productRepository.findAll());
		model.addAttribute("labours", appUserRepository.findByUserType_Name(Constants.LABOUR));
		return "manufacture";
	} 
	
	@PostMapping("/save")
	public String save(@ModelAttribute("manufacture") Manufacture manufacture,Model model)
	{
		
	 manufactureRepository.save(manufacture);
	 return "redirect:/manufacture";
	}

	@GetMapping("/edit/{id}")
	public String update(@PathVariable("id") long id,Model model)
	{
		Manufacture manufacture=manufactureRepository.findById(id).orElse(null);
		model.addAttribute("manufacture",manufacture);
		fillModel(model);
		return "manufactureEdit";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id)
	{
		manufactureRepository.deleteById(id);
		return "manufactureSearch";
	}
	@GetMapping("/search")
	public String searchGet(Model model) {
		model.addAttribute("manufactureSearch",new ManufactureSearch());
		fillModel(model);
		return "manufactureSearch";
	}
	
	@PostMapping("/search")
	public String searchPost(ManufactureSearch manufactureSearch,Model model,HttpSession session) {
		session.setAttribute("manufactureSearch",manufactureSearch);
		int page=1;
		pagination(page, manufactureSearch, model);
		return "manufactureSearch";
	}
	
	@GetMapping("/pageno={page}")
	public String page(@PathVariable("page") int page,HttpSession session,Model model){
		ManufactureSearch manufactureSearch = (ManufactureSearch) session.getAttribute("manufactureSearch");
		pagination(page, manufactureSearch, model);
		return "manufactureSearch";
	}
	
	public void pagination(int page, ManufactureSearch manufactureSearch, Model model) {

		page = (page > 0) ? page : 1;
		from = Constants.ROWS * (page - 1);
		records = (long) manufactureRepository.count(manufactureSearch);
		total = (int) Math.ceil((double) records / (double) Constants.ROWS);
		List<ManufactureDTO> manufactures = manufactureRepository.searchPaginated(manufactureSearch,from);
		model.addAttribute("totalPages", total);
		model.addAttribute("currentPage", page);
		model.addAttribute("manufactures", manufactures);
		model.addAttribute("manufactureSearch", manufactureSearch);
		System.out.println("total records: " + records + " total Pages: " + total + " Current page: " + page);
		fillModel(model);

	}
	private void fillModel(Model model) {
		String[] userTypes = { Constants.LABOUR };
		model.addAttribute("labours", appUserRepository.findByUserType_NameIn(userTypes));
		model.addAttribute("products", productRepository.findAll());
		model.addAttribute("sizes", sizeRepository.findAll());
		
	}
}
