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

import com.akash.entity.LabourGroup;
import com.akash.repository.LabourGroupRepository;

@Controller
@RequestMapping("/labour-group")
public class LabourGroupController {

	@Autowired
	LabourGroupRepository labourGroupRepository;

	@GetMapping
	public String add(Model model, HttpSession session) {
		if (!model.asMap().containsKey("labourGroup")) {
			model.addAttribute("labourGroup", new LabourGroup());
		}

		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.labourGroup", model.asMap().get("result"));
		}
		int page = 1;
		session.setAttribute("currentPage", page);
		pagination(page, model);

		return "labourGroup";

	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("labourGroup") LabourGroup labourGroup, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			redirect.addFlashAttribute("labourGroup", labourGroup);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/labour-group";
		}
		else if(labourGroupRepository.existsByName(labourGroup.getName()))
		{
			redirect.addFlashAttribute("labourGroup", labourGroup);
			redirect.addFlashAttribute("fail","LabourGroup Already Exixts");
			return "redirect:/labour-group";
		}
		else {

			labourGroupRepository.save(labourGroup);
			redirect.addFlashAttribute("success", "LabourGroup Saved Successfully");
			return "redirect:/labour-group";
		}
	}

	@GetMapping("/edit/{id}")
	public String update(@PathVariable("id") long id, Model model, HttpSession session) {

		if (!model.asMap().containsKey("labourGroup")) {
			model.addAttribute("labourGroup", labourGroupRepository.findById(id));
		}
		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.labourGroup", model.asMap().get("result"));
		}
		model.addAttribute("edit", true);
		int page = (int) session.getAttribute("currentPage");
		pagination(page, model);
		return "labourGroup";
	}
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("labourGroup") LabourGroup labourGroup,BindingResult result,RedirectAttributes redirect,HttpSession session,Model model)
	{
		
		  int page = (int) session.getAttribute("currentPage"); 
		  pagination(page,model);
		 
		if (result.hasErrors())
		{
			redirect.addFlashAttribute("labourGroup", labourGroup);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/labour-group/edit/"+labourGroup.getId();
		}
		else if(labourGroupRepository.checkLabourGroupAlreadyExists(labourGroup.getName(),labourGroup.getId()) != null)
		{
			redirect.addFlashAttribute("labourGroup", labourGroup);
			redirect.addFlashAttribute("fail","LabourGroup Already Exists");
			return "redirect:/labour-group/edit/" +labourGroup.getId();
		}
		else
		{
			labourGroupRepository.save(labourGroup);
			redirect.addFlashAttribute("success","LabourGroup Updated Successfully");
			
			return "redirect:/labour-group/pageno=" + page;
		}
		
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id,HttpSession session,RedirectAttributes redirect) {
	int page= (int) session.getAttribute("currentPage");
		labourGroupRepository.deleteById(id);
		redirect.addFlashAttribute("success", "LabourGroup Deleted Successfully");
		return "redirect:/labour-group/pageno=" +page ;
	}

	@GetMapping("/pageno={page}")
	public String paginate(@PathVariable("page") int page, Model model, HttpSession session) {
		session.setAttribute("currentPage", page);
		pagination(page, model);
		model.addAttribute("labourGroup", new LabourGroup());
		return "labourGroup";
	}

	public void pagination(int page, Model model) {

		page = page <= 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 10);
		Page<LabourGroup> list = labourGroupRepository.findAll(pageable);
		System.out.println(list.getContent());
		model.addAttribute("list", list.getContent());
		model.addAttribute("currentPage", page + 1);

		model.addAttribute("totalPages", list.getTotalPages());
	}

}
