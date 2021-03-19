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
import com.akash.entity.AppUserSearch;
import com.akash.entity.RawMaterial;
import com.akash.entity.RawMaterialSearch;
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
	
	int from = 0;
	int total = 0;
	int ROWS = 10;
	Long records = 0L;
	
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
	@GetMapping("/search")
	public String list(Model model,HttpSession session) {
		fillModel(model);
		model.addAttribute("appUserSearch", new AppUserSearch());
		//model.addAttribute("totalPages", 1);
		session.setAttribute("currentPage", 1);
		

		return "userList";
	}

	@PostMapping("/search")
	public String searchOrders(AppUserSearch appUserSearch, Model model, HttpSession session) {

		int page = 1;
		session.setAttribute("appUserSearch",appUserSearch);
		pagination(page, appUserSearch, model, session);
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
		//int page = (int) session.getAttribute("currentPage");
		//pagination(page, model);
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
			
			return "redirect:/app-user/" + page;
		}
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id,HttpSession session,RedirectAttributes redirect)
	{
		int page= (int) session.getAttribute("currentPage");
		appUserRepository.deleteById(id);
		redirect.addFlashAttribute("success","User Deleted Successfully");
		return "redirect:/app-user/" +page;
	}
		
	
	private void fillModel(Model model)
	{
		model.addAttribute("UserList", userRepository.findAll());
		model.addAttribute("siteList", siteRepository.findAll());
	}

	public void pagination(int page, AppUserSearch appUserSearch, Model model, HttpSession session) {

		page = (page > 0) ? page : 1;
		from = ROWS * (page - 1);
		records = (long) appUserRepository.searchAppUsersCount(appUserSearch);
		total = (int) Math.ceil((double) records / (double) ROWS);
		List<AppUser> appUsers = appUserRepository.searchAppUserPaginated(appUserSearch, from);
		model.addAttribute("totalPages", total);
		session.setAttribute("currentPage", page);
		model.addAttribute("appUser",appUsers);
		model.addAttribute("appUserSearch", appUserSearch);
		System.out.println("total records: " + records + " total Pages: " + total + " Current page: " + page);
		fillModel(model);

	}
	@GetMapping("/{page}")
	public String showAppUser(@PathVariable("page") int page, Model model, HttpSession session) {
		AppUserSearch appUserSearch =  (AppUserSearch) session.getAttribute("appUserSearch");
		
		pagination(page, appUserSearch, model, session);
		return "userList";

	}
}
