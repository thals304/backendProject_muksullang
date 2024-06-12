package com.application.muksullang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.application.muksullang.service.PostService;
/*
 * 게시물 등록, 수정, 삭제는 admin이 해주는 것이므로 
 * PostController는 화면 이동과 게시물 조회만 해주면 될 것 같음 (근데 뿌리는 걸 어떻게 하지? admin에서 만들고 post에 있는 html에 뿌리면 되는건가?)
 * 
 * */
@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;
	
	// 웹페이지 첫 화면 main 
	@GetMapping("/main")
	public String main() {
		return "post/main";
	}
	
	// Best Of (post_id를 가져와서 db에서 post_nm이 recommend인 게시물 정보를 html에 뿌려주면 될 것 같음)
	@GetMapping("/bestOf")
	public String bestOf() {
		return "post/bestOf";
	}
	
	// Best Of - Post 디테일 가져오기 (post_id를 가져와서 db에서 getDetail하고 html에 뿌려주면 될 것 같음
	@GetMapping("/bestOfDetail")
	public String bestOfDetail() {
		return "";
	}
	
	// Recommend (post_id를 가져와서 db에서 post_nm이 recommend인 게시물 정보를 html에 뿌려주면 될 것 같음)
	@GetMapping("/recommend")
	public String recommend() {
		return "post/recommend";
	}
	
	// Recommend - Post의 디테일 가져오기(post_id를 가져와서 db에서 getDetail하고 html에 뿌려주면 될 것 같음
	@GetMapping("/recommedDetail")
	public String recommendDetail() {
		return "";
	}
	
	// About
	@GetMapping("/about")
	public String about() {
		return "post/about";
	}
	
}
