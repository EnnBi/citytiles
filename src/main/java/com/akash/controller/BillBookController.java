package com.akash.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.akash.entity.AppUser;
import com.akash.entity.BillBook;
import com.akash.entity.BillBookSearch;
import com.akash.entity.Sales;
import com.akash.entity.dto.BillBookDTO;
import com.akash.repository.AppUserRepository;
import com.akash.repository.BillBookRepository;
import com.akash.repository.ColorRepository;
import com.akash.repository.LabourGroupRepository;
import com.akash.repository.ProductRepository;
import com.akash.repository.SiteRepository;
import com.akash.repository.SizeRepository;
import com.akash.repository.VehicleRepository;
import com.akash.util.Constants;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;


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
	@Autowired
	LabourGroupRepository labourGroupRepository;
	@Autowired
	ColorRepository colorRepo;
	
	int from = 0;
	int total = 0;
	Long records = 0L;

	@GetMapping
	public String add(Model model,HttpServletResponse response) {
		model.addAttribute("billBook", new BillBook());
		String[] userTypes = { Constants.CUSTOMER, Constants.CONTRACTOR };
		model.addAttribute("customers", appUserRepository.findAppUsersOnType(userTypes,true));
		model.addAttribute("vehicles", vehicleRepo.findAll());
		model.addAttribute("products", productRepository.findAll());
		model.addAttribute("labourGroups", labourGroupRepository.findAll());
		model.addAttribute("colors", colorRepo.findAll());
		if(model.asMap().containsKey("print")){
			BillBook billBook = (BillBook) model.asMap().get("billBookPrint");
			printBillBook(billBook, response);
		}
		
		if(model.asMap().containsKey("printChallan")){
			BillBook billBook = (BillBook) model.asMap().get("billBookPrint");
			printChallanBillBook(billBook.getId(), response);
		}
		
		return "billBook";
	}

	@RequestMapping(value="/save",params="save",method=RequestMethod.POST)
	public String save(@ModelAttribute("billBook") BillBook billBook, Model model,RedirectAttributes redirectAttributes) {
		if(billBook.getVehicle() != null)  
			billBook.setDriver(billBook.getVehicle().getDriver());
		if(billBook.getLoadingAmount() !=null || billBook.getUnloadingAmount()!=null)
			setLoadingAndUnloadingCharges(billBook);
		
		billBook.getSales().forEach(s->s.setBillBook(billBook));
		billBookRepository.save(billBook);
		redirectAttributes.addFlashAttribute("success","Bill Book saved successfully");
		return "redirect:/bill-book";
	}
	
	@RequestMapping(value="/save",params="print",method=RequestMethod.POST)
	public String printAndSaveBillBook(@ModelAttribute("billBook") BillBook billBook, Model model,RedirectAttributes redirectAttributes,HttpServletResponse response){
		if(billBook.getVehicle() != null)
			billBook.setDriver(billBook.getVehicle().getDriver());
		if(billBook.getLoadingAmount() !=null || billBook.getUnloadingAmount()!=null)
			setLoadingAndUnloadingCharges(billBook);
		billBookRepository.save(billBook);
		redirectAttributes.addFlashAttribute("success","Bill Book saved successfully");
		redirectAttributes.addFlashAttribute("print",true);
		redirectAttributes.addFlashAttribute("billBookPrint",billBook);
		return "redirect:/bill-book";

	}
	
	@RequestMapping(value="/save",params="printChallan",method=RequestMethod.POST)
	public String printChallanAndSaveBillBook(@ModelAttribute("billBook") BillBook billBook, Model model,RedirectAttributes redirectAttributes,HttpServletResponse response){
		if(billBook.getVehicle() != null)
			billBook.setDriver(billBook.getVehicle().getDriver());
		if(billBook.getLoadingAmount() !=null || billBook.getUnloadingAmount()!=null)
			setLoadingAndUnloadingCharges(billBook);
		billBookRepository.save(billBook);
		redirectAttributes.addFlashAttribute("success","Bill Book saved successfully");
		redirectAttributes.addFlashAttribute("printChallan",true);
		redirectAttributes.addFlashAttribute("billBookPrint",billBook);
		return "redirect:/bill-book";

	}

	@GetMapping("/edit/{id}")
	public String update(@PathVariable("id") long id, Model model) {

		BillBook billBook =  billBookRepository.findById(id).orElse(null);
		model.addAttribute("billBook",billBook );
		String[] userTypes = { Constants.CUSTOMER, Constants.CONTRACTOR };
		model.addAttribute("customers", appUserRepository.findByUserType_NameInAndActive(userTypes,true));
		List<AppUser> labours = appUserRepository.findByUserType_NameAndActive(Constants.LABOUR,true);
		if(billBook.getDriver()!=null)
			labours.add(billBook.getDriver());
		model.addAttribute("labours", labours);
		model.addAttribute("vehicles", vehicleRepo.findAll());
		model.addAttribute("products", productRepository.findAll());
		model.addAttribute("sites", siteRepository.findAll());
		model.addAttribute("sizes", sizeRepository.findAll());
		model.addAttribute("labourGroups", labourGroupRepository.findAll());
		model.addAttribute("colors", colorRepo.findAll());
		return "billBookEdit";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id,RedirectAttributes redirectAttributes) {
		billBookRepository.deleteById(id);
		redirectAttributes.addFlashAttribute("success","Bill Book deleted successfully");
		return "redirect:/bill-book/search";
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
	
	@GetMapping("/print/{id}")
	public void printBillBook(@PathVariable long id,HttpServletResponse response){
		BillBook billBook = billBookRepository.findById(id).get();
		Double txblAmt = billBook.getTotal()- (billBook.getTotal()*0.18);
		billBook.setTxblAmt(txblAmt);
		printBillBook(billBook, response);
	}
	
	@GetMapping("/printChallan/{id}")
	public void printChallanBillBook(@PathVariable long id,HttpServletResponse response){
		BillBook billBook = billBookRepository.findById(id).get();
		billBook.setTotal(null);
		billBook.setCgst(null);
		billBook.setSgst(null);
		billBook.setDiscount(null);
		billBook.setPaid(null);
		billBook.setBalance(null);
		billBook.getSales().forEach(b->{
		b.setAmount(null);
		b.setUnitPrice(null);
		});
		printBillBook(billBook, response);
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
		model.addAttribute("customers", appUserRepository.findByUserType_NameInAndActive(userTypes,true));
		model.addAttribute("vehicles", vehicleRepo.findAll());
		model.addAttribute("sites", siteRepository.findAll());
		model.addAttribute("labourGroups", labourGroupRepository.findAll());
		model.addAttribute("colors", colorRepo.findAll());
		
	}
	
	void setLoadingAndUnloadingCharges(BillBook billBook){
		if (billBook.getLoaders().size() > 0) {
			AppUser driver = null;
			
			if(billBook.getDriver() != null)
				driver = billBook.getLoaders().stream().filter(l->l.getId() == billBook.getDriver().getId()).findFirst().orElse(null);
			
			if(driver == null){
				Double loadingAmtPerHead = billBook.getLoadingAmount() / billBook.getLoaders().size();
				billBook.setLoadingAmountPerHead(loadingAmtPerHead);
			}
			else{
				Double driverLoadingCharge = billBook.getLoadingAmount()/2;
				billBook.setDriverLoadingCharges(driverLoadingCharge);
				Double loadingAmtPerHead = driverLoadingCharge / (billBook.getLoaders().size()-1);
			    billBook.setLoadingAmountPerHead(loadingAmtPerHead);
			}
		}

		if (billBook.getUnloaders().size() > 0) {
			AppUser driver = null;
			
			if(billBook.getDriver() != null)
				driver = billBook.getUnloaders().stream().filter(l->l.getId() == billBook.getDriver().getId()).findFirst().orElse(null);
			
			if(driver == null){
				Double unloadingAmtPerHead = billBook.getUnloadingAmount() / billBook.getUnloaders().size();
				billBook.setUnloadingAmountPerHead(unloadingAmtPerHead);
			}
			else{
				Double driverUnloadingCharge = billBook.getUnloadingAmount()/3;
				billBook.setDriverUnloadingCharges(driverUnloadingCharge);
				Double labourUnloadingCharge = ((billBook.getUnloadingAmount()/3)*2)/(billBook.getLoaders().size()-1);
				billBook.setUnloadingAmountPerHead(labourUnloadingCharge);
			}
			
			
		}
	}
	 
	public void printBillBook(BillBook billBook,HttpServletResponse response){
		InputStream mainJasperStream = this.getClass().getResourceAsStream("/BillBook.jasper");
		InputStream subJasperStream = this.getClass().getResourceAsStream("/SalesDetail.jasper");
		
		try {
			JasperReport mainReport =  (JasperReport) JRLoader.loadObject(mainJasperStream);
			JasperReport salesReport =  (JasperReport) JRLoader.loadObject(subJasperStream);
			
			Map<String,Object> params =  new HashMap<>();
			params.put("sales", salesReport);
			JRDataSource data = new JRBeanCollectionDataSource(Arrays.asList(billBook));
			JasperPrint jasperPrint  = JasperFillManager.fillReport(mainReport, params, data);
			response.setContentType("application/pdf");
			String fileName = billBook.getReceiptNumber()+"_"+LocalDate.now();
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".pdf");
			OutputStream  output = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
