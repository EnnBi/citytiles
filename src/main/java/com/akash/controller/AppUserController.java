package com.akash.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.akash.entity.AppUser;
import com.akash.entity.MaterialType;
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
		} else {

			appUserRepository.save(appUser);
			redirect.addFlashAttribute("success", "Appuser Saved Successfully");
			return "redirect:/app-user";
		}
	}
	
	private void fillModel(Model model)
	{
		model.addAttribute("UserList", userRepository.findAll());
		model.addAttribute("siteList", siteRepository.findAll());
	}

}
