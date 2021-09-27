package com.akash.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.akash.entity.LabourCost;
import com.akash.repository.AppUserRepository;
import com.akash.repository.LabourCostRepository;
import com.akash.repository.LabourGroupRepository;
import com.akash.repository.ProductRepository;
import com.akash.repository.SizeRepository;
import com.akash.util.Constants;

@Controller
@RequestMapping("/labour-cost")
public class LabourCostController {
	@Autowired
	LabourCostRepository labourCostRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	SizeRepository sizeRepository;
	@Autowired
	LabourGroupRepository labourGroupRepository;
	@Autowired
	AppUserRepository appUserRepo;
	
	@GetMapping
	public String add(Model model,HttpSession session)
	{
		if (!model.asMap().containsKey("labourCost")) {
			model.addAttribute("labourCost", new LabourCost());
		}

		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.labourCost", model.asMap().get("result"));
		}
		int page = 1;
		session.setAttribute("currentPage", page);
		pagination(page, model);

		return "labourCost";
				
	}
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("labourCost") LabourCost labourCost,BindingResult result,Model model,RedirectAttributes redirect)
	{
		if (result.hasErrors()) {
			redirect.addFlashAttribute("labourCost", labourCost);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/labour-cost";
		}
		/*
		 * else if(labourCostRepository.existsByProductAndLabourGroupAndSize(labourCost.
		 * getProduct(), labourCost.getLabourGroup(),labourCost.getSize())) {
		 * redirect.addFlashAttribute("labourCost", labourCost);
		 * redirect.addFlashAttribute("fail", "Labour Rate Already Exists"); return
		 * "redirect:/labour-cost"; }
		 */
		
		
		else {

			labourCostRepository.save(labourCost);
			redirect.addFlashAttribute("success", "Labour Rate Saved Successfully");
			return "redirect:/labour-cost";
		}
	}
	
	@GetMapping("/edit/{id}")
	public String update(@PathVariable("id") long id,Model model,HttpSession session)
	{
		if (!model.asMap().containsKey("labourCost")) {
			model.addAttribute("labourCost", labourCostRepository.findById(id));
		}
		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.labourCost", model.asMap().get("result"));
		}
		model.addAttribute("edit", true);
		int page = (int) session.getAttribute("currentPage");
		pagination(page, model);
		return "labourCost";
	}
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("labourCost") LabourCost labourCost,BindingResult result,RedirectAttributes redirect,HttpSession session,Model model)
	{
		
		  int page = (int) session.getAttribute("currentPage"); 
		  pagination(page,model);
		 
		if (result.hasErrors())
		{
			redirect.addFlashAttribute("labourCost", labourCost);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/labour-cost/edit/"+labourCost.getId();
		}
		/*
		 * else if(labourCostRepository.existsByProductAndLabourGroupAndSizeAndIdNot(
		 * labourCost.getProduct(),labourCost.getLabourGroup(),labourCost.getSize(),
		 * labourCost.getId())) { redirect.addFlashAttribute("labourCost", labourCost);
		 * redirect.addFlashAttribute("fail", "Labour Rate Already Exists"); return
		 * "redirect:/labour-cost/edit/" +labourCost.getId(); }
		 */
		else
		{
			labourCostRepository.save(labourCost);
			redirect.addFlashAttribute("success","Labour Rate Updated Successfully");
			
			return "redirect:/labour-cost/pageno=" + page;
		}
		
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id,HttpSession session,RedirectAttributes redirect)
	{
		int page = (int) session.getAttribute("currentPage");
		try {
			labourCostRepository.deleteById(id);
			redirect.addFlashAttribute("success", "Labour Rate Deleted Successfully");
		} catch (Exception e) {
			redirect.addFlashAttribute("fail", "Labour Rate cannot be deleted");
		}
		
		return "redirect:/labour-cost/pageno=" +page;
	}
		
	
	@GetMapping("/pageno={page}")
	public String paginate(@PathVariable("page") int page, Model model, HttpSession session) {
		session.setAttribute("currentPage", page);
		pagination(page, model);
		model.addAttribute("labourCost", new LabourCost());
		return "labourCost";
	}

	public void pagination(int page, Model model) {

		page = page <= 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, Constants.ROWS);
		Page<LabourCost> list = labourCostRepository.findAll(pageable);
		System.out.println(list.getContent());
		model.addAttribute("list", list.getContent());
		model.addAttribute("currentPage", page + 1);
		fillModel(model);
		model.addAttribute("totalPages", list.getTotalPages());
	}

	public void fillModel(Model model){
		model.addAttribute("labourGroups", labourGroupRepository.findAll());
		model.addAttribute("sizes", sizeRepository.findAll());
		model.addAttribute("products", productRepository.findAll());
		model.addAttribute("labours",appUserRepo.findByUserType_NameAndActive(Constants.LABOUR, true));
	}


}
