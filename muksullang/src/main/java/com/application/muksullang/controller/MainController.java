package com.application.muksullang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping //localhost , 먹슐랭.com
	public String main() {
		return "redirect:/post/main";
	}
	
}
