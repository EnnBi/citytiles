package com.akash.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akash.entity.BillBook;
import com.akash.entity.BillBookSearch;
import com.akash.entity.dto.BillBookDTO;
import com.akash.repository.AppUserRepository;
import com.akash.repository.BillBookRepository;
import com.akash.repository.ProductRepository;
import com.akash.repository.SiteRepository;
import com.akash.repository.SizeRepository;
import com.akash.repository.VehicleRepository;
import com.akash.util.Constants;


@Controller
@RequestMapping("/bill-book")
public class BillBookController {

	@Autowired
	BillBookRepository billBookRepository;

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	ProductRepository productRepository;
	@Autowired
	VehicleRepository vehicleRepo;
	@Autowired
	SiteRepository siteRepository;
	@Autowired
	SizeRepository sizeRepository;
	
	int from = 0;
	int total = 0;
	Long records = 0L;

	@GetMapping
	public String add(Model model) {
		model.addAttribute("billBook", new BillBook());
		String[] userTypes = { Constants.CUSTOMER, Constants.CONTRACTOR };
		model.addAttribute("customers", appUserRepository.findAppUsersOnType(userTypes));
		model.addAttribute("labours", appUserRepository.findByUserType_Name(Constants.LABOUR));
		model.addAttribute("vehicles", vehicleRepo.findAll());
		model.addAttribute("products", productRepository.findAll());
		return "billBook";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("billBook") BillBook billBook, Model model) {

		if (billBook.getLoaders().size() > 0) {
			Double loadingAmtPerHead = billBook.getLoadingAmount() / billBook.getLoaders().size();
			billBook.setLoadingAmountPerHead(loadingAmtPerHead);
		}

		if (billBook.getUnloaders().size() > 0) {
			Double unloadingAmtPerHead = billBook.getUnloadingAmount() / billBook.getUnloaders().size();
			billBook.setUnloadingAmountPerHead(unloadingAmtPerHead);
		}
		billBookRepository.save(billBook);

		return "redirect:/bill-book";
	}

	@GetMapping("/edit/{id}")
	public String update(@PathVariable("id") long id, Model model) {

		BillBook billBook =  billBookRepository.findById(id).orElse(null);
		model.addAttribute("billBook",billBook );
		String[] userTypes = { Constants.CUSTOMER, Constants.CONTRACTOR };
		model.addAttribute("customers", appUserRepository.findByUserType_NameIn(userTypes));
		model.addAttribute("labours", appUserRepository.findByUserType_Name(Constants.LABOUR));
		model.addAttribute("vehicles", vehicleRepo.findAll());
		model.addAttribute("products", productRepository.findAll());
		model.addAttribute("sites", siteRepository.findAll());
		model.addAttribute("sizes", sizeRepository.findAll());
		return "billBookEdit";
	}

	@GetMapping("/delete")
	public String delete(@PathVariable("id") long id) {
		billBookRepository.deleteById(id);
		return null;
	}

	@GetMapping("/search")
	public String searchGet(Model model) {
		model.addAttribute("billBookSearch",new BillBookSearch());
		fillModel(model);
		return "billBookSearch";
	}
	
	@PostMapping("/search")
	public String searchPost(BillBookSearch billBookSearch,Model model,HttpSession session) {
		session.setAttribute("billBookSearch",billBookSearch);
		int page=1;
		pagination(page, billBookSearch, model);
		return "billBookSearch";
	}
	
	@GetMapping("/pageno={page}")
	public String page(@PathVariable("page") int page,HttpSession session,Model model){
		BillBookSearch billBookSearch = (BillBookSearch) session.getAttribute("billBookSearch");
		pagination(page, billBookSearch, model);
		return "billBookSearch";
	}

	@GetMapping("/receipt/{number}")
	public ResponseEntity<?> checkIfReceiptNoExists(@PathVariable String number) {
		return ResponseEntity.ok(billBookRepository.existsByReceiptNumber(number));
	}
	
	public void pagination(int page, BillBookSearch billBookSearch, Model model) {

		page = (page > 0) ? page : 1;
		from = Constants.ROWS * (page - 1);
		records = (long) billBookRepository.count(billBookSearch);
		total = (int) Math.ceil((double) records / (double) Constants.ROWS);
		List<BillBookDTO> billBooks = billBookRepository.searchPaginated(billBookSearch,from);
		model.addAttribute("totalPages", total);
		model.addAttribute("currentPage", page);
		model.addAttribute("billBooks", billBooks);
		model.addAttribute("billBookSearch", billBookSearch);
		System.out.println("total records: " + records + " total Pages: " + total + " Current page: " + page);
		fillModel(model);

	}

	private void fillModel(Model model) {
		String[] userTypes = { Constants.CUSTOMER, Constants.CONTRACTOR };
		model.addAttribute("customers", appUserRepository.findByUserType_NameIn(userTypes));
		model.addAttribute("vehicles", vehicleRepo.findAll());
		model.addAttribute("sites", siteRepository.findAll());
		
	}
}
