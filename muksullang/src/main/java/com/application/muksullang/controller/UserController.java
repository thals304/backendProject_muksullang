package com.application.muksullang.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.core.io.Resource;
import com.application.muksullang.dto.UserDTO;
import com.application.muksullang.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Value("${file.repo.path}")
	private String fileRepositoryPath;
	

	@GetMapping("/signUp")
	public String signUp() {
		return "user/signUp";
	}
	
	@PostMapping("/signUp")
	public String signUp(@ModelAttribute UserDTO userDTO) {
		// form에서 데이터 잘 넘어오는지 확인 
		System.out.println(userDTO);
		
		userService.signUp(userDTO);
		
		return "redirect:/user/signIn";
	}
	
	@PostMapping("/validId")
	@ResponseBody
	public String validId(@RequestParam("userId") String userId) {
		// .ajax에서 id 잘 넘어오는지 확인
		//System.out.println(userId);
		
		return userService.checkValidId(userId);
	}
	
	@GetMapping("/signIn")
	public String signIn() {
		return "user/signIn";
	}
	
	@PostMapping("/signIn")
	@ResponseBody
	public String signIn(@RequestBody UserDTO userDTO, HttpServletRequest request) {
	
		String isValidUser = "n"; 
		
		// .ajax에서 id 잘 넘어오는지 확인
		System.out.println(userDTO.getUserId());
		
		if (userService.signIn(userDTO)) { // 인증 되었을 경우

			HttpSession session = request.getSession(); // 세션 객체 생성
			session.setAttribute("userId", userDTO.getUserId());
			
			isValidUser = "y";
		}
		
		return isValidUser;
	}
	
	@GetMapping("/myPage")
	public String myPage() {
        
        return "user/myPage";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "redirect:/user/myPage";
	}
	
	@GetMapping("/update")
	public String update(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		
		model.addAttribute("userDTO", userService.getUserDetail((String)session.getAttribute("userId")));
		
		return "user/update";
		
	}
	
	// 수정하기에서 이미지 수정, 비밀번호, 이메일, 주소 중 일부만 바꿀 수도 있는거 아닌가?
	@PostMapping("/update")
	@ResponseBody
	public String update(@RequestParam("uploadProfile") MultipartFile uploadProfile, @ModelAttribute UserDTO userDTO) throws IllegalStateException, IOException {
		// update.html에서 값이 잘 넘어오고 있는지 확인하기 위함
		System.out.println(userDTO);
		
		userService.updateUser(uploadProfile, userDTO);
		
		String jsScript = """
				<script>
					location.href = '/user/myPage';
				</script>""";
			
		return jsScript;
	}
	
	@GetMapping("/thumbnails")
	@ResponseBody
	public Resource thumbnails(@RequestParam("fileName") String fileName) throws MalformedURLException {
	     return new UrlResource("file:" + fileRepositoryPath + fileName); // 전달된 파일명으로 썸네일 객체를 반환한다.
	}
	
	@GetMapping("/delete")
	public String delete() {
		return "user/delete";
	}
	
	@PostMapping("/delete")
	public String delete(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		// session에 로그인한 userId가 잘 담겨있는지 확인
		System.out.println((String)session.getAttribute("userId"));
		
		userService.updateInactiveUser((String)session.getAttribute("userId"));
		session.invalidate();
		
		return "redirect:/user/myPage"; // 아직 /post/main이 없어 myPage로 가도록 했는데 만들면 바꿀수도 있음
	}
}
