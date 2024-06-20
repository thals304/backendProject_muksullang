package com.application.muksullang.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.application.muksullang.dto.AdminDTO;
import com.application.muksullang.dto.ContentDTO;
import com.application.muksullang.dto.PostDTO;
import com.application.muksullang.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
/*
 * 문제 발생- 
 * 
 * admin html에서도 웹 메인으로 이동 가능한 버튼이 있으면 좋겠음 > 그리고 post 게시물에서 session.adminId != null인 경우에 게시물을 삭제/수정 가능 하도록
 * 
 * 웹사이트에서 localhost/admin/main 이나 /createPost를 눌러도 아무나 들어갈 수 있음 session으로 막아야 될 듯
 * */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Value("${image.repo.path}")
	private String imageRepositoryPath;
	
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
	
	@GetMapping("/logOut")
	public String logOut(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		// /post/main으로 보낼 지 아니면 /admin/signIn으로 보낼 지 고민중
		
		return "redirect:/admin/signIn";
	}
	
	@GetMapping("/createPost")
	public String createPost() {
		return "admin/createPost";
	}
	
	@PostMapping("/createPost")
	public String createPost(@RequestParam("uploadImage") MultipartFile uploadImage, 
			                 @ModelAttribute PostDTO postDTO,
			                 @ModelAttribute ContentDTO contentDTO, 
			                 Model model)throws IllegalStateException, IOException {
		 
		adminService.createPost(uploadImage , postDTO);
		
		// adminService.createPost을 통해 postId가 잘 넘어오는지 확인하기 위함
		//System.out.println(postDTO.getPostId());	
		
		 // postId를 contentDTO에 설정
	    List<ContentDTO> contentList = contentDTO.getContentDTOList();
	    for (ContentDTO dto : contentList) {
	        dto.setPostId(postDTO.getPostId()); // postId 설정
	    }

	    // 내용 생성 서비스 호출
	    adminService.createContent(contentList);
	    
		return "redirect:/admin/main";
	}
	
	
}
