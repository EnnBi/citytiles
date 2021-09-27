package com.akash.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.akash.entity.Color;
import com.akash.repository.ColorRepository;

@Controller
@RequestMapping("/color")
public class ColorController {

	@Autowired
	ColorRepository colorRepo;

	@GetMapping
	public String add(Model model, HttpSession session) {
		if (!model.asMap().containsKey("color")) {
			model.addAttribute("color", new Color());
		}

		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.color", model.asMap().get("result"));
		}
		
		model.addAttribute("colors", colorRepo.findAll());
		return "color";

	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("color") Color color, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			redirect.addFlashAttribute("color", color);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/color";
		} else if (colorRepo.existsByName(color.getName())) {
			redirect.addFlashAttribute("color", color);
			redirect.addFlashAttribute("fail", "Color Already Exixts");
			return "redirect:/color";
		} else {

			colorRepo.save(color);
			redirect.addFlashAttribute("success", "Color Saved Successfully");
			return "redirect:/color";
		}
	}

	@GetMapping("/edit/{id}")
	public String update(@PathVariable("id") long id, Model model, HttpSession session) {

		if (!model.asMap().containsKey("color")) {
			model.addAttribute("color", colorRepo.findById(id));
		}
		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.color", model.asMap().get("result"));
		}
		model.addAttribute("edit", true);
		model.addAttribute("colors", colorRepo.findAll());
		return "color";
	}

	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("color") Color color, BindingResult result, RedirectAttributes redirect,
			HttpSession session, Model model) {

		if (result.hasErrors()) {
			redirect.addFlashAttribute("color", color);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/color/edit/" + color.getId();
		} else if (colorRepo.checkColorAlreadyExists(color.getName(), color.getId()) != null) {
			redirect.addFlashAttribute("color", color);
			redirect.addFlashAttribute("fail", "Color Already Exists");
			return "redirect:/color/edit/" + color.getId();
		} else {
			colorRepo.save(color);
			redirect.addFlashAttribute("success", "Color Updated Successfully");

			return "redirect:/color";
		}

	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id, HttpSession session, RedirectAttributes redirect) {
		colorRepo.deleteById(id);
		redirect.addFlashAttribute("success", "Color Deleted Successfully");
		return "redirect:/color";
	}
}
