package com.akash.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
import com.akash.entity.UserType;
import com.akash.repository.AppUserRepository;
import com.akash.repository.UserTypeRepository;

@Controller
@RequestMapping("/user-type")
public class UserTypeController {
	@Autowired
	UserTypeRepository userRepository;
	@Autowired
	AppUserRepository appUserRepository;
	
	@GetMapping
	public String add(Model model,HttpSession session)
	{
		if (!model.asMap().containsKey("user")) {
			model.addAttribute("user", new UserType());
		}

		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.user", model.asMap().get("result"));
		}
		int page = 1;
		session.setAttribute("currentPage", page);
		pagination(page, model);

		return "userType";
				
	}
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("user") UserType userType,BindingResult result,Model model,RedirectAttributes redirect)
	{
		if (result.hasErrors()) {
			redirect.addFlashAttribute("user", userType);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/user-type";
		} else if(userRepository.existsByName(userType.getName()))
		{
			redirect.addFlashAttribute("user", userType);
			redirect.addFlashAttribute("fail", "Person Type Already Exists");
			return "redirect:/user-type";
		}
		
		
		else {

			userRepository.save(userType);
			redirect.addFlashAttribute("success", "Person Type Saved Successfully");
			return "redirect:/user-type";
		}
	}
	@GetMapping("/edit/{id}")
	public String update(@PathVariable("id") long id,Model model,HttpSession session)
	{
		if (!model.asMap().containsKey("user")) {
			model.addAttribute("user", userRepository.findById(id));
		}
		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.user", model.asMap().get("result"));
		}
		model.addAttribute("edit", true);
		int page = (int) session.getAttribute("currentPage");
		pagination(page, model);
		return "userType";
	}
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("user") UserType userType,BindingResult result,RedirectAttributes redirect,HttpSession session,Model model)
	{
		
		  int page = (int) session.getAttribute("currentPage"); 
		  pagination(page,model);
		 
		if (result.hasErrors())
		{
			redirect.addFlashAttribute("user", userType);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/user-type/edit/"+userType.getId();
		}
		
		else if(userRepository.chechUserAlreadyExists(userType.getName(), userType.getId()) != null)
		{
			redirect.addFlashAttribute("user", userType);
			redirect.addFlashAttribute("fail", "Person Type Already Exists");
			return "redirect:/user-type/edit/"+userType.getId();
		}
		else
		{
		userRepository.save(userType);
			redirect.addFlashAttribute("success","Person Type Updated Successfully");
			
			return "redirect:/user-type/pageno=" + page;
		}
		
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id,HttpSession session,RedirectAttributes redirect)
	{
		int page= (int) session.getAttribute("currentPage");
		userRepository.deleteById(id);
		redirect.addFlashAttribute("success","UserType Deleted Successfully");
		return "redirect:/user-type/pageno=" +page;
	}
	@GetMapping("/pageno={page}")
	public String paginate(@PathVariable("page") int page, Model model, HttpSession session) {
		session.setAttribute("currentPage", page);
		pagination(page, model);
		model.addAttribute("user", new UserType());
		return "userType";
	}

	@GetMapping("/{name}/users")
	public ResponseEntity<?> usersOnUserType(@PathVariable String name){
		return ResponseEntity.ok(appUserRepository.findByUserType_Name(name));
	}
	
	public void pagination(int page, Model model) {

		page = page <= 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 10);
		Page<UserType> list =userRepository.findAll(pageable);
		System.out.println(list.getContent());
		model.addAttribute("list", list.getContent());
		model.addAttribute("currentPage", page + 1);

		model.addAttribute("totalPages", list.getTotalPages());
	}



}
