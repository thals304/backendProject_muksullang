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

import com.application.muksullang.dto.ContentDTO;
import com.application.muksullang.dto.PostDTO;
import com.application.muksullang.dto.ReplyDTO;
import com.application.muksullang.service.PostService;
import com.application.muksullang.service.ReplyService;


/*
 *  //(중요) mapper에서 resultMap -> hashmap을 사용해서 매핑하려고 한다면, JOIN을 해서 SELECT하고 싶은 컬럼들이 resultMap 요소에 다 들어가 있어야 사용 가능 
 * 
 * 게시물 내용에서 br \n 적용이 안되는 고민 생김 > 
 * recommend/recommendDetail에서 th:text 날짜를 바꾸면 에러가 남
 * AWS 배포에서 layout2에서 오류 발생
 * 
 * Best Of 구현 안한 부분
 * - 커서 페이지네이션
 * - 북마크(게시물 찜하기) : Best Of에서 북마크 버튼 생성은 했지만 아직 기능 구현 하지 않음 !(좀 더 연구 필요)
 * 버튼을 눌렀을 때 빨간색이 되면 북마크로 등록, 빨간색에서 다시 버튼을 눌러 흰색이 되면 북마크 취소 
 * - 공공데이터 csv 넣기(서울 관광 음식 - 위치에 송파 들어가는 것 중 5-6개 정도만 뽑아 사용> 사진 찾아야함)
 * */
@Controller
@RequestMapping("/post")
public class PostController {

	@Value("${image.repo.path}")
	private String imageRepositoryPath;
	
	@Value("${file.repo.path}")
	private String fileRepositoryPath;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private ReplyService replyService;
	
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
	
	// post 썸네일
	@GetMapping("/thumbnails")
	@ResponseBody
	// import org.springframework.core.io.Resource;
	// import org.springframework.core.io.URLResource;
	public Resource thumbnails(@RequestParam("fileName") String fileName)  throws MalformedURLException {
		return new UrlResource("file:"+ imageRepositoryPath + fileName);
	}
	
	// user 썸네일
	@GetMapping("/userThumbnails")
	@ResponseBody
	public Resource userThumbnails(@RequestParam("fileName") String fileName) throws MalformedURLException {
		return new UrlResource("file:"+ fileRepositoryPath + fileName);
	}
	
	
	// Best Of Search
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
	
	// Best Of Detail
	@GetMapping("/bestOfDetail")
	public String bestOfDetail(Model model, @RequestParam("postId") long postId) {
		
		model.addAttribute("postDTO", postService.getBestOfDetailPost(postId));
		model.addAttribute("contentDTOList", postService.getBestOfDetailContent(postId));
		model.addAttribute("contentImpactMsg", postService.getBestOfDetailContentImpactMsg(postId));		
		model.addAttribute("replyList", replyService.getReplyList(postId));
		model.addAttribute("replyCnt", replyService.getReplyCnt(postId));
		
		return "post/bestOfDetail";
	}
	
	// Recommend
	@GetMapping("/recommend")
	public String recommend(Model model) {
		
		String type = "Recommend";
		List<PostDTO> recommendList = postService.getRecommendList(type);
		
		// 각 게시물의 postId를 가지고 content 1개도 같이 html에 보내야함
		for(PostDTO postDTO : recommendList) {
			Long postId = postDTO.getPostId();
			String content = postService.getRecommendContent(postId);
			model.addAttribute("content_" + postId, content); // content_${postId}로 템플릿에서 사용 가능
		}
		model.addAttribute("recommendList", recommendList);
		
		return "post/recommend";
	}
	
	// Recommend Detail
	@GetMapping("/recommedDetail")
	public String recommendDetail(Model model, @RequestParam("postId") long postId) {
		
		model.addAttribute("postDTO", postService.getRecommendDetailPost(postId));
		// content 개수 조절을 어떻게 해야될지 모르겠음
		model.addAttribute("replyList", replyService.getReplyList(postId));
		model.addAttribute("replyCnt", replyService.getReplyCnt(postId));
		
		return "post/recommendDetail";
	}
	
	// About
	@GetMapping("/about")
	public String about() {
		return "post/about";
	}
	
}
