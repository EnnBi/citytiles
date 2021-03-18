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

import com.akash.entity.Site;
import com.akash.entity.Size;
import com.akash.repository.SizeRepository;

@Controller
@RequestMapping("/size")
public class SizeController {
	@Autowired
	SizeRepository sizeRepository;
	
	@GetMapping
	public String add(Model model,HttpSession session)
	{
		if (!model.asMap().containsKey("size")) {
			model.addAttribute("size", new Size());
		}

		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.size", model.asMap().get("result"));
		}
		int page = 1;
		session.setAttribute("currentPage", page);
		pagination(page, model);

		return "size";
				
	}
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("size") Size size,BindingResult result,Model model,RedirectAttributes redirect)
	{
		if (result.hasErrors()) {
			redirect.addFlashAttribute("size", size);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/size";
		}
		else if(sizeRepository.existsByName(size.getName()))
		{
			redirect.addFlashAttribute("size", size);
			redirect.addFlashAttribute("fail", "Size Already Exists");
			return "redirect:/size";
		}
		
		
		else {

			sizeRepository.save(size);
			redirect.addFlashAttribute("success", "Size Saved Successfully");
			return "redirect:/size";
		}
	}
	
	@GetMapping("/edit/{id}")
	public String update(@PathVariable("id") long id,Model model,HttpSession session)
	{
		if (!model.asMap().containsKey("size")) {
			model.addAttribute("size", sizeRepository.findById(id));
		}
		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.size", model.asMap().get("result"));
		}
		model.addAttribute("edit", true);
		int page = (int) session.getAttribute("currentPage");
		pagination(page, model);
		return "size";
	}
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("size") Size size,BindingResult result,RedirectAttributes redirect,HttpSession session,Model model)
	{
		
		  int page = (int) session.getAttribute("currentPage"); 
		  pagination(page,model);
		 
		if (result.hasErrors())
		{
			redirect.addFlashAttribute("size", size);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/size/edit/"+size.getId();
		}
		else if(sizeRepository.checkSizeAlreadyExists(size.getName(), size.getId()) != null)
		{
			redirect.addFlashAttribute("size", size);
			redirect.addFlashAttribute("fail", "Size Already Exists");
			return "redirect:/size/edit/" +size.getId();
		}
		else
		{
			sizeRepository.save(size);
			redirect.addFlashAttribute("success","Site Updated Successfully");
			
			return "redirect:/size/pageno=" + page;
		}
		
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id,HttpSession session,RedirectAttributes redirect)
	{
		int page = (int) session.getAttribute("currentPage");
		sizeRepository.deleteById(id);
		redirect.addFlashAttribute("success", "Size Deleted Successfully");
		return "redirect:/size/pageno=" +page;
	}
	@GetMapping("/pageno={page}")
	public String paginate(@PathVariable("page") int page, Model model, HttpSession session) {
		session.setAttribute("currentPage", page);
		pagination(page, model);
		model.addAttribute("size", new Size());
		return "size";
	}

	public void pagination(int page, Model model) {

		page = page <= 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 2);
		Page<Size> list = sizeRepository.findAll(pageable);
		System.out.println(list.getContent());
		model.addAttribute("list", list.getContent());
		model.addAttribute("currentPage", page + 1);

		model.addAttribute("totalPages", list.getTotalPages());
	}



}
