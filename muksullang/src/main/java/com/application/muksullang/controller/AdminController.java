package com.application.muksullang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.muksullang.dto.AdminDTO;
import com.application.muksullang.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
/*
 * 문제 발생- 
 * Admin의 main 화면으로 갈 때 admin dashboard랑 싱크가 안맞아서 화면이 제대로 나오지 않음 
 * > html에서 div가 일치되지 않아서 그런거 같은데
 * */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@GetMapping("/signIn")
	public String signIn() {
		return "admin/signIn";
	}
	
	@PostMapping("/signIn")
	@ResponseBody
	public String signIn(@RequestBody AdminDTO adminDTO, HttpServletRequest request) {
		
		String isValidAdmin = "n";
		
		AdminDTO resultDTO = adminService.signIn(adminDTO);
		if (resultDTO != null) {
			
			HttpSession session = request.getSession();
			session.setAttribute("adminId", resultDTO.getAdminId());
			session.setAttribute("role", resultDTO.getRole());
			
			isValidAdmin = "y";
		}
		
		return isValidAdmin;
	}
	
	@GetMapping("/main")
	public String adminPage() {
		return "admin/main";
	}
}
