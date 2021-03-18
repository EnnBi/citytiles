package com.akash.controller;

import java.time.LocalDate;

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
import com.akash.entity.RawMaterial;
import com.akash.repository.AppUserRepository;
import com.akash.repository.MaterialTypeRepository;
import com.akash.repository.RawMaterialRepository;

@Controller
@RequestMapping("/raw-material")
public class RawMaterialController {
	@Autowired
	RawMaterialRepository rawMaterialRepository;
	@Autowired
	MaterialTypeRepository materialRepository;
	@Autowired
	AppUserRepository userRepository;

	@GetMapping
	public String add(Model model) {

		fillModel(model);
		if (!model.asMap().containsKey("rawMaterial")) {
			model.addAttribute("rawMaterial", new RawMaterial());
		}

		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.rawMaterial", model.asMap().get("result"));
		}
		return "rawMaterial";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("rawMaterial") RawMaterial rawMaterial, BindingResult result, Model model,
			RedirectAttributes redirect) {

		if (result.hasErrors()) {
			redirect.addFlashAttribute("rawMaterial", rawMaterial);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter all the field correctly");
			return "redirect:/raw-material";
		}

		else if (rawMaterialRepository.existsByChalanNumber(rawMaterial.getChalanNumber())){
			redirect.addFlashAttribute("rawMaterial", rawMaterial);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Raw Material Already Exists");
			return "redirect:/raw-material";
		}

		else {

			rawMaterial.setDate(LocalDate.now());
			rawMaterialRepository.save(rawMaterial);
			redirect.addFlashAttribute("success", "Raw Material Saved Successfully");
			return "redirect:/raw-material";
		}
	}

	@GetMapping("/list")
	public String list(Model model) {
		int page = 1;
		pagination(page, model);

		return "rawList";
	}

	@GetMapping("/edit/{id}")
	public String update(@PathVariable("id") long id, Model model, HttpSession session) {
		fillModel(model);
		if (!model.asMap().containsKey("rawMaterial")) {
			model.addAttribute("rawMaterial", rawMaterialRepository.findById(id));
		}
		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.rawMaterial", model.asMap().get("result"));
		}
		model.addAttribute("edit", true);
		int page = (int) session.getAttribute("currentPage");
		pagination(page, model);
		return "rawMaterial";
	}

	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("rawMaterial") RawMaterial rawMaterial, BindingResult result,
			RedirectAttributes redirect, HttpSession session, Model model) {

		int page = (int) session.getAttribute("currentPage");
		// pagination(page,model);

		if (result.hasErrors()) {
			redirect.addFlashAttribute("rawMaterial", rawMaterial);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter all the fields correctly");
			return "redirect:/raw-material/edit/" + rawMaterial.getId();
		}

		/*
		 * else if(appUserRepository.chechUserExistsAlready(appUser.getContact(),
		 * appUser.getAccountNumber(), appUser.getId()) !=null) {
		 * redirect.addFlashAttribute("user", appUser);
		 * redirect.addFlashAttribute("fail", "User Alredy Exists"); return
		 * "redirect:/app-user/edit/"+appUser.getId(); }
		 */
		else {
			rawMaterialRepository.save(rawMaterial);
			redirect.addFlashAttribute("success", "Raw Material Updated Successfully");

			return "redirect:/raw-material/pageno=" + page;
		}
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id, HttpSession session, RedirectAttributes redirect) {

		int page = (int) session.getAttribute("currentPage");
		rawMaterialRepository.deleteById(id);
		redirect.addFlashAttribute("success", "Raw Material Deleted Successfully");
		return "redirect:/raw-material/pageno=" + page;
	}

	private void fillModel(Model model) {
		model.addAttribute("userList", userRepository.findAll());
		model.addAttribute("rawList", materialRepository.findAll());
	}

	public void pagination(int page, Model model) {

		page = page <= 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 2);
		Page<RawMaterial> list = rawMaterialRepository.findAll(pageable);
		System.out.println(list.getContent());
		model.addAttribute("list", list.getContent());
		model.addAttribute("currentPage", page + 1);

		model.addAttribute("totalPages", list.getTotalPages());
	}

	@GetMapping("/pageno={page}")
	public String paginate(@PathVariable("page") int page, Model model, HttpSession session) {
		session.setAttribute("currentPage", page);
		pagination(page, model);
		return "rawList";
	}

}
