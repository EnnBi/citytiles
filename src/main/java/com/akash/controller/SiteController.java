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
import com.akash.repository.SiteRepository;

@Controller
@RequestMapping("/site")
public class SiteController {

	@Autowired
	SiteRepository siteRepository;

	@GetMapping
	public String add(Model model, HttpSession session) {
		if (!model.asMap().containsKey("site")) {
			model.addAttribute("site", new Site());
		}

		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.site", model.asMap().get("result"));
		}
		int page = 1;
		session.setAttribute("currentPage", page);
		pagination(page, model);

		return "site";

	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("site") Site site, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			redirect.addFlashAttribute("site", site);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/site";
		} else {

			siteRepository.save(site);
			redirect.addFlashAttribute("success", "Site Saved Successfully");
			return "redirect:/site";
		}
	}

	@GetMapping("/edit/{id}")
	public String update(@PathVariable("id") long id, Model model, HttpSession session) {

		if (!model.asMap().containsKey("site")) {
			model.addAttribute("site", siteRepository.findById(id));
		}
		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.site", model.asMap().get("result"));
		}
		model.addAttribute("edit", true);
		int page = (int) session.getAttribute("currentPage");
		pagination(page, model);
		return "site";
	}
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("site") Site site,BindingResult result,RedirectAttributes redirect,HttpSession session,Model model)
	{
		
		  int page = (int) session.getAttribute("currentPage"); 
		  pagination(page,model);
		 
		if (result.hasErrors())
		{
			redirect.addFlashAttribute("site", site);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/site/edit/"+site.getId();
		}
		else
		{
			siteRepository.save(site);
			redirect.addFlashAttribute("success","Site Updated Successfully");
			
			return "redirect:/site/pageno=" + page;
		}
		
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id,HttpSession session,RedirectAttributes redirect) {
	int page= (int) session.getAttribute("currentPage");
		siteRepository.deleteById(id);
		redirect.addFlashAttribute("success", "Site Deleted Successfully");
		return "redirect:/site/pageno=" +page ;
	}

	@GetMapping("/pageno={page}")
	public String paginate(@PathVariable("page") int page, Model model, HttpSession session) {
		session.setAttribute("currentPage", page);
		pagination(page, model);
		model.addAttribute("site", new Site());
		return "site";
	}

	public void pagination(int page, Model model) {

		page = page <= 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 2);
		Page<Site> list = siteRepository.findAll(pageable);
		System.out.println(list.getContent());
		model.addAttribute("list", list.getContent());
		model.addAttribute("currentPage", page + 1);

		model.addAttribute("totalPages", list.getTotalPages());
	}

}
