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

import com.akash.entity.Product;
import com.akash.entity.Vehicle;
import com.akash.repository.AppUserRepository;
import com.akash.repository.VehicleRepository;
import com.akash.util.Constants;

@Controller
@RequestMapping("/vehicle")
public class VehicleController {
	@Autowired
	VehicleRepository vehicleRepository;

	@Autowired
	AppUserRepository userRepository;

	@GetMapping
	public String add(Model model, HttpSession session) {
		if (!model.asMap().containsKey("vehicle")) {
			model.addAttribute("vehicle", new Vehicle());
		}

		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.vehicle", model.asMap().get("result"));
		}
		int page = 1;
		session.setAttribute("currentPage", page);
		pagination(page, model);
		return "vehicle";

	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("vehicle") Vehicle vehicle, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			redirect.addFlashAttribute("vehicle", vehicle);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the fields correctly");
			return "redirect:/vehicle";
		}

		else if (vehicleRepository.existsByNumber(vehicle.getNumber())) {
			redirect.addFlashAttribute("vehicle", vehicle);
			redirect.addFlashAttribute("fail", "Vehicle Already Exists");
			return "redirect:/vehicle";
		}

		else {

			vehicleRepository.save(vehicle);
			redirect.addFlashAttribute("success", "Vehicle Saved Successfully");
			return "redirect:/vehicle";
		}
	}

	@GetMapping("/edit/{id}")
	public String update(@PathVariable("id") long id, Model model, HttpSession session) {
		if (!model.asMap().containsKey("vehicle")) {
			model.addAttribute("vehicle", vehicleRepository.findById(id));
		}
		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.vehicle", model.asMap().get("result"));
		}
		model.addAttribute("edit", true);
		int page = (int) session.getAttribute("currentPage");
		pagination(page, model);
		return "vehicle";
	}

	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("vehicle") Vehicle vehicle, BindingResult result,
			RedirectAttributes redirect, HttpSession session, Model model) {

		int page = (int) session.getAttribute("currentPage");
		pagination(page, model);

		if (result.hasErrors()) {
			redirect.addFlashAttribute("vehicle", vehicle);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/vehicle/edit/" + vehicle.getId();
		}

		else if (vehicleRepository.checkVehicleAlreadyExists(vehicle.getNumber(), vehicle.getId()) != null) {
			redirect.addFlashAttribute("vehicle", vehicle);
			redirect.addFlashAttribute("fail", "Vehicle Already Exists");
			return "redirect:/vehicle/edit/" + vehicle.getId();
		}

		else {
			vehicleRepository.save(vehicle);
			redirect.addFlashAttribute("success", "Vehicle Updated Successfully");

			return "redirect:/vehicle/pageno=" + page;
		}
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id, HttpSession session, RedirectAttributes redirect) {

		int page = (int) session.getAttribute("currentPage");
		vehicleRepository.deleteById(id);
		redirect.addFlashAttribute("success", "Vehicle Deleted Successfully");
		return "redirect:/vehicle/pageno=" + page;
	}

	@GetMapping("/pageno={page}")
	public String paginate(@PathVariable("page") int page, Model model, HttpSession session) {
		session.setAttribute("currentPage", page);
		pagination(page, model);
		model.addAttribute("vehicle", new Vehicle());
		return "vehicle";
	}

	public void pagination(int page, Model model) {

		page = page <= 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 2);
		Page<Vehicle> list = vehicleRepository.findAll(pageable);
		System.out.println(list.getContent());
		model.addAttribute("list", list.getContent());
		model.addAttribute("currentPage", page + 1);
		model.addAttribute("userList", userRepository.findByUserType_Name(Constants.DRIVER));

		model.addAttribute("totalPages", list.getTotalPages());
	}

}
