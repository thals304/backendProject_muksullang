package com.application.muksullang.controller;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.muksullang.dto.PostDTO;
import com.application.muksullang.service.PostService;


/*
 * Best Of 구현 안한 부분
 * 
 * - 커서 페이지네이션
 * - 북마크(게시물 찜하기) : Best Of에서 북마크 버튼 생성은 했지만 아직 기능 구현 하지 않음 !(좀 더 연구 필요)
 * 버튼을 눌렀을 때 빨간색이 되면 북마크로 등록, 빨간색에서 다시 버튼을 눌러 흰색이 되면 북마크 취소 
 * - 디테일에 리뷰 수, 리뷰 뿌리기
 * */
@Controller
@RequestMapping("/post")
public class PostController {

	@Value("${image.repo.path}")
	private String imageRepositoryPath;
	
	@Autowired
	private PostService postService;
	
	// 웹페이지 첫 화면 main 
	@GetMapping("/main")
	public String main() {
		return "post/main";
	}
	
	// Best Of (post_id를 가져와서 db에서 post_nm이 recommend인 게시물 정보를 html에 뿌려주면 될 것 같음)
	@GetMapping("/bestOf")
	public String bestOf(Model model) {
		// 게시물 수가 9개 이상일 때 다음 페이지로 넘어가는 페이지네이션 기능을 만들어야 함
		
		String type = "Best Of";
		model.addAttribute("bestOfList", postService.getBestOfList(type));
		
		return "post/bestOf";
	}
	
	@GetMapping("/thumbnails")
	@ResponseBody
	// import org.springframework.core.io.Resource;
	// import org.springframework.core.io.URLResource;
	public Resource thumbnails(@RequestParam("fileName") String fileName)  throws MalformedURLException {
		return new UrlResource("file:"+ imageRepositoryPath + fileName);
	}
	
	// Best Of의 검색 기능
	@GetMapping("/searchBestOfPost")
	public String searchBestOfPost(@RequestParam(name = "title") String title,
            @RequestParam(name = "category") String category,
            @RequestParam(name = "location") String location, Model model) {
		
		 Map<String, Object> params = new HashMap<>();
		 params.put("title", title);
		 params.put("category", category);
		 params.put("location", location);
		
		List<PostDTO> searchResults = postService.searchBestOfPost(params);
		
		model.addAttribute("bestOfList", searchResults);
		
		return "post/bestOf";
	}
	
	// Best Of - Post 디테일 가져오기 (post_id를 가져와서 db에서 getDetail하고 html에 뿌려주면 될 것 같음
	@GetMapping("/bestOfDetail")
	public String bestOfDetail(Model model, @RequestParam("postId") long postId) {
		
		model.addAttribute("postDTO", postService.getBestOfDetailPost(postId) );
		model.addAttribute("contentDTOList", postService.getBestOfDetailContent(postId));
		model.addAttribute("contentImpactMsg", postService.getBestOfDetailContentImpactMsg(postId));		
		return "post/bestOfDetail";
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
