package com.application.muksullang.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
import com.application.muksullang.service.PostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
/*
 * 문제 발생- 
 *  bestOfPost가 업데이트가 안되고, detail로 화면이동이 안됨 -> update.html에서 hidden으로 postId를 안보내서 생긴 문제
 * 	dashboard를 클릭 했을 때, /admin/main으로 이동이 안됨 -> 해결완료
 *  html다 session.adminId가 있을 경우에만 접근 가능하도록 해야함
 *  
 * */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Value("${image.repo.path}")
	private String imageRepositoryPath;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private PostService postService;
	
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
	public String adminPage(Model model) {
		model.addAttribute("userCnt", adminService.getUserCnt()); // activeYn이 y인 회원 수 카운트
		model.addAttribute("bestOfCnt", adminService.getBestOfPostCnt());
		model.addAttribute("recommendCnt", adminService.getRecommendPostCnt());
		model.addAttribute("postCnt", adminService.getBestOfPostCnt());
		model.addAttribute("replyCnt", adminService.getReplyCnt());
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
	
	@GetMapping("/bestOfList")
	public String bestOfList(Model model) {
		model.addAttribute("bestOfList", postService.getBestOfList("Best Of"));
		
		return "admin/bestOfList";
	}
	
	@GetMapping("/recommendList")
	public String recommendList(Model model) {
		
		model.addAttribute("recommendList", postService.getRecommendList("Recommend"));
		
		return "admin/recommendList";
	}
	
	@GetMapping("/updateBestOfPost")
	public String updateBestOfPost(@RequestParam("postId") long postId, Model model) {
		
		// th:object를 쓰기 위해 postDTO에 List<ContentDTO>를 추가함
		PostDTO postDTO = postService.getBestOfDetailPost(postId);
		List<ContentDTO> contentList = adminService.getBestOfDetailContent(postId);
		postDTO.setContentList(contentList);
		model.addAttribute("postDTO", postDTO);
		
		return "admin/updateBestOfPost";
	}
	
	@PostMapping("/updateBestOfPost")
	@ResponseBody
	public String updateBestOfPost(@ModelAttribute PostDTO postDTO, @RequestParam("uploadImage") MultipartFile uploadImage) throws IllegalStateException, IOException {
		
		adminService.updateBestOfPost(postDTO, uploadImage);

		String jsScript = "";
		jsScript += "<script>";
		jsScript += "alert('게시물 수정 완료 되었습니다.');";
		jsScript += "location.href ='/post/bestOfDetail?postId="+ postDTO.getPostId()+"';";
		jsScript += "</script>";
		return jsScript;
	}
	
	@GetMapping("/deleteBestOfPost")
	public String deleteBestOfPost(@RequestParam("postId") long postId) {
		
		adminService.deleteBestOfPost(postId);
		
		return "redirect:/admin/bestOfList";
	}
	
	
}
