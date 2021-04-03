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

import com.akash.entity.MaterialType;
import com.akash.entity.Size;
import com.akash.repository.MaterialTypeRepository;
import com.akash.repository.SiteRepository;

@Controller
@RequestMapping("/material-type")
public class MaterialTypeController {
	@Autowired
	MaterialTypeRepository materialTypeRepository;
	@GetMapping
	public String add(Model model,HttpSession session)
	{
		if (!model.asMap().containsKey("material")) {
			model.addAttribute("material", new MaterialType());
		}

		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.material", model.asMap().get("result"));
		}
		int page = 1;
		session.setAttribute("currentPage", page);
		pagination(page, model);

		return "material";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("material") MaterialType materialType,BindingResult result,Model model,RedirectAttributes redirect)
	{
		if (result.hasErrors()) {
			redirect.addFlashAttribute("material", materialType);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/material-type";
		}
		else if(materialTypeRepository.existsByName(materialType.getName()))
		{
			redirect.addFlashAttribute("material", materialType);
			redirect.addFlashAttribute("fail", "Material Type Already Exixts");
			return "redirect:/material-type";
		}
		
		
		else {

			materialTypeRepository.save(materialType);
			redirect.addFlashAttribute("success", "MaterialType Saved Successfully");
			return "redirect:/material-type";
		}
	}
	
	@GetMapping("/edit/{id}")
	public String update(@PathVariable("id") long id,Model model,HttpSession session)
	{
		if (!model.asMap().containsKey("material")) {
			model.addAttribute("material", materialTypeRepository.findById(id));
		}
		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.material", model.asMap().get("result"));
		}
		model.addAttribute("edit", true);
		int page = (int) session.getAttribute("currentPage");
		pagination(page, model);
		return "material";
	}
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("material") MaterialType materialType,BindingResult result,RedirectAttributes redirect,HttpSession session,Model model)
	{
		
		  int page = (int) session.getAttribute("currentPage"); 
		  pagination(page,model);
		 
		if (result.hasErrors())
		{
			redirect.addFlashAttribute("material", materialType);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/material-type/edit/"+materialType.getId();
		}
		
		else if(materialTypeRepository.checkMaterialAlreadyExists(materialType.getName(), materialType.getId()) != null)
		{
			redirect.addFlashAttribute("material", materialType);
			redirect.addFlashAttribute("fail","MaterialType Already Exists");
			return "redirect:/material-type/edit/" +materialType.getId();
		}
		else
		{
			materialTypeRepository.save(materialType);
			redirect.addFlashAttribute("success","MaterialType Updated Successfully");
			
			return "redirect:/material-type/pageno=" + page;
		}
		
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id,RedirectAttributes redirect,HttpSession session)
	{
		int page = (int) session.getAttribute("currentPage");
		materialTypeRepository.deleteById(id);
		redirect.addFlashAttribute("success", "MaterialType Deleted Successfully");
		return "redirect:/material-type/pageno=" +page;
	}
	@GetMapping("/pageno={page}")
	public String paginate(@PathVariable("page") int page, Model model, HttpSession session) {
		session.setAttribute("currentPage", page);
		pagination(page, model);
		model.addAttribute("material", new MaterialType());
		return "material";
	}

	public void pagination(int page, Model model) {

		page = page <= 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 10);
		Page<MaterialType> list =materialTypeRepository.findAll(pageable);
		System.out.println(list.getContent());
		model.addAttribute("list", list.getContent());
		model.addAttribute("currentPage", page + 1);

		model.addAttribute("totalPages", list.getTotalPages());
	}

	
}
