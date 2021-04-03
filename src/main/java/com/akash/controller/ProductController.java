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
import com.akash.entity.Product;
import com.akash.entity.Site;
import com.akash.repository.ProductRepository;
import com.akash.repository.SizeRepository;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	SizeRepository sizeRepository;
	@GetMapping
	public String add(Model model,HttpSession session)
	{
		
		if (!model.asMap().containsKey("product")) {
			model.addAttribute("product", new Product());
		}

		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.product", model.asMap().get("result"));
		}
		int page=1;
		session.setAttribute("currentPage", page);
		pagination(page, model);
		return "product";


				
	}
	@PostMapping("/save")
	public String save(@Valid@ModelAttribute("product") Product product,BindingResult result,Model model,RedirectAttributes redirect)
	{
		if (result.hasErrors()) {
			redirect.addFlashAttribute("product", product);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the fields correctly");
			return "redirect:/product";
		}
		else if(productRepository.existsByName(product.getName()))
		{
			redirect.addFlashAttribute("product", product);
			redirect.addFlashAttribute("fail","Product Already Exists");
			return "redirect:/product";
		}
		else {

			productRepository.save(product);
			redirect.addFlashAttribute("success", "Product Saved Successfully");
			return "redirect:/product";
		}
	}
	
	@GetMapping("/edit/{id}")
	public String update(@PathVariable("id") long id,Model model,HttpSession session)
	{
		if (!model.asMap().containsKey("product")) {
			model.addAttribute("product", productRepository.findById(id));
		}
		if (model.asMap().containsKey("result")) {
			model.addAttribute("org.springframework.validation.BindingResult.product", model.asMap().get("result"));
		}
		model.addAttribute("edit", true);
		int page = (int) session.getAttribute("currentPage");
		pagination(page, model);
		return "product";
	}
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("product") Product product,BindingResult result,RedirectAttributes redirect,HttpSession session,Model model)
	{
		
		  int page = (int) session.getAttribute("currentPage"); 
		  pagination(page,model);
		 
		if (result.hasErrors())
		{
			redirect.addFlashAttribute("product", product);
			redirect.addFlashAttribute("result", result);
			redirect.addFlashAttribute("fail", "Please enter the field correctly");
			return "redirect:/product/edit/"+product.getId();
		}
		
		else if(productRepository.checkProductAlreadyExists(product.getName(), product.getId()) != null)
		{
			redirect.addFlashAttribute("product", product);
			redirect.addFlashAttribute("fail","Product Already Exists");
			return "redirect:/product/edit/" +product.getId();
		}
		else
		{
			productRepository.save(product);
			redirect.addFlashAttribute("success","Product Updated Successfully");
			
			return "redirect:/product/pageno=" + page;
		}
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id,RedirectAttributes redirect,HttpSession session)
	{
		int page = (int) session.getAttribute("currentPage");
		productRepository.deleteById(id);
		redirect.addFlashAttribute("success", "Product Deleted Successfully");
		return "redirect:/product/pageno=" +page;
	}
	
	@GetMapping("/{id}/sizes")
	public ResponseEntity<?> getSizesOnProduct(@PathVariable long id){
		return ResponseEntity.ok(productRepository.findSizesOnPrductId(id));
	}
	
	

	@GetMapping("/pageno={page}")
	public String paginate(@PathVariable("page") int page, Model model, HttpSession session) {
		session.setAttribute("currentPage", page);
		pagination(page, model);
		model.addAttribute("product", new Product());
		return "product";
	}

	public void pagination(int page, Model model) {

		page = page <= 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 10);
		Page<Product> list = productRepository.findAll(pageable);
		System.out.println(list.getContent());
		model.addAttribute("list", list.getContent());
		model.addAttribute("currentPage", page + 1);
		model.addAttribute("sizeList", sizeRepository.findAll());

		model.addAttribute("totalPages", list.getTotalPages());
	}
}
