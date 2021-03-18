package com.akash.controller;

import java.util.List;

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

import com.akash.entity.AppUser;
import com.akash.repository.AppUserRepository;
import com.akash.repository.SiteRepository;
import com.akash.repository.UserTypeRepository;

@Controller
@RequestMapping("/app-user")
public class AppUserController {
	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	SiteRepository siteRepository;
	@Autowired
	UserTypeRepository userRepository;
	@GetMapping
	public String get(Model model)
	{
		fillModel(model);
		if (!model.asMap().containsKey("user")) {
			model.addAttribute("user", new AppUser());
		}

		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.user", model.asMap().get("result"));
		}
		return "appUser";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("user") AppUser appUser,BindingResult result,Model model,RedirectAttributes redirect)
	{
		if (result.hasErrors()) {
			redirect.addFlashAttribute("user", appUser);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter all the field correctly");
			return "redirect:/app-user";
		}else if (appUserRepository.existsByContactOrAccountNumber(appUser.getContact(), appUser.getAccountNumber()))
		{
			redirect.addFlashAttribute("user", appUser);
			redirect.addFlashAttribute("fail", "User Alread Exists");
			return "redirect:/app-user";
		}
		
		
		else {

			appUserRepository.save(appUser);
			redirect.addFlashAttribute("success", "User Saved Successfully");
			return "redirect:/app-user";
		}
	}
	@GetMapping("/list")
	public String list(Model model)
	{
		int page=1;
		pagination(page, model);
	
		return "userList";
	}
	@GetMapping("/edit/{id}")
	public String update(@PathVariable("id") long id,Model model,HttpSession session)
	{
		fillModel(model);
		if (!model.asMap().containsKey("user")) {
			model.addAttribute("user", appUserRepository.findById(id));
		}
		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.user", model.asMap().get("result"));
		}
		model.addAttribute("edit", true);
		int page = (int) session.getAttribute("currentPage");
		pagination(page, model);
		return "appUser";
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("user") AppUser appUser,BindingResult result,RedirectAttributes redirect,HttpSession session,Model model)
	{
		
		  int page = (int) session.getAttribute("currentPage"); 
		//  pagination(page,model);
		 
		if (result.hasErrors())
		{
			redirect.addFlashAttribute("user", appUser);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/app-user/edit/"+appUser.getId();
		}
		
		else if(appUserRepository.chechUserExistsAlready(appUser.getContact(), appUser.getAccountNumber(), appUser.getId()) !=null)
		{
			redirect.addFlashAttribute("user", appUser);
			redirect.addFlashAttribute("fail", "User Alredy Exists");
			return "redirect:/app-user/edit/"+appUser.getId();
		}
		else
		{
		appUserRepository.save(appUser);
			redirect.addFlashAttribute("success","User Updated Successfully");
			
			return "redirect:/app-user/pageno=" + page;
		}
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id,HttpSession session,RedirectAttributes redirect)
	{
		int page= (int) session.getAttribute("currentPage");
		appUserRepository.deleteById(id);
		redirect.addFlashAttribute("success","User Deleted Successfully");
		return "redirect:/app-user/pageno=" +page;
	}
		
	
	private void fillModel(Model model)
	{
		model.addAttribute("UserList", userRepository.findAll());
		model.addAttribute("siteList", siteRepository.findAll());
	}

	public void pagination(int page, Model model) {

		page = page <= 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 2);
		Page<AppUser> list =appUserRepository.findAll(pageable);
		System.out.println(list.getContent());
		model.addAttribute("list", list.getContent());
		model.addAttribute("currentPage", page + 1);

		model.addAttribute("totalPages", list.getTotalPages());
	}
	@GetMapping("/pageno={page}")
	public String paginate(@PathVariable("page") int page, Model model, HttpSession session) {
		session.setAttribute("currentPage", page);
		pagination(page, model);
		return "userList";
	}
}
