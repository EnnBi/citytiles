package com.akash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(){
		return "index";
	}
	
	@GetMapping("/template")
	public String template(){
		return "template";
	}
}
