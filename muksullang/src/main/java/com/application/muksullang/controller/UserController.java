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
/* 
 * 회원가입
 * > 문제발생 : 회원가입 로그인/비밀번호 중복확인 메세지가 안뜸 & 이메일 수신동의 체크가 안됨 > url을 validId로 쓰지 않은 실수 / label for와 checkbox id를 일치시켜줘야함
 *         : userId와 passwd가 null로 넘어옴 > html에 userId, passwd는 name이 반드시 필요!
 *         : agreeYn, emailYn의 값이 안넘어옴 > id, name이 dto 변수명과 같아야함
 *         : form에서 주소 입력에서 00시 00구 00동 구조로 입력 안하면 다시 입력하도록 경고 주는 건 어떻게 하지?
 * 로그인    
 *  > 문제발생 : html에서 Post로 userId가 null로 넘어옴 > .ajax에서 id가 없는데 name속성으로 안써주고 id로 써서 생긴 문제
 *        
 * 마이페이지 수정하기 > 일부만 수정해도 업데이트 되도록 
 * > 문제발생 : 수정하기에서 파일 업로드 안하면 current request is not a multipart request 오류 발생 > form에서 class를 지웠더니 해결 완료
 *         : serviceImpl에서 업로드 파일이 default 파일과 같은 경우를 따로 조건으로 달아줘야 하는가? yes
 *         : serviceImpl에서 비밀번호가 null로 넘어왔을 때도 암호화가 되는가? no
 * */
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
		System.out.println(userId);
		
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
