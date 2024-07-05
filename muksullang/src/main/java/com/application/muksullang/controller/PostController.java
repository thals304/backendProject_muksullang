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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.muksullang.dto.ContentDTO;
import com.application.muksullang.dto.PostDTO;
import com.application.muksullang.dto.ReplyDTO;
import com.application.muksullang.service.PostService;
import com.application.muksullang.service.ReplyService;

/*
 *  
 *  //(중요) mapper에서 resultMap -> hashmap을 사용해서 매핑하려고 한다면, JOIN을 해서 SELECT하고 싶은 컬럼들이 resultMap 요소에 다 들어가 있어야 사용 가능 
 * 
 * 게시물 내용 textarea에서 br \n 적용이 안되는 고민 생김 > ckeditor 또는 utext로 해서 textarea에서 <br> 넣기
 * 
 * bestOfDetail에서 similarSortList가 원활하게 작동을 안함
 * aop 공부 후 사용해봐야함/test 코드도
 * 프로젝트 구현 안한 부분
 * - 커서 페이지네이션 (best of.html / recommend.html)
 * - 북마크(게시물 찜하기) : bestOf/recommend.html에서 북마크 버튼 생성은 했지만 아직 기능 구현 하지 않음 !(좀 더 연구 필요)
 * 버튼을 눌렀을 때 빨간색이 되면 북마크로 등록, 빨간색에서 다시 버튼을 눌러 흰색이 되면 북마크 취소 
 * - 공공데이터 csv import 해서 넣기(서울 관광 음식 - 위치에 송파 들어가는 것 중 5-6개 정도만 뽑아 사용> 사진 찾아야함)
 * - 게시물 수정/삭제 > 수정하기가 고민인게 best Of는 content 3개 recommend는 recipe : 개 article : 개로
 * 개수도 다르고 들어가는 구성도 다른데 수정하기 폼에서 이전 저장된 자료를 어떻게 뿌려줘야 할지 
 * - 찜하기 기능 완성 후 가능 > my page에서 찜한 게시물 & 리뷰 작성한 게시물 모아보기 (게시물이 많으면 페이지네이션? 해야하나)
 * - main 화면에 어떤 게시물을 뿌릴지 (스케쥴링으로 달마다 enrollAt이 desc인 것 몇 개만 뽑아쓰는게 가능할까?)
 * - (선택사항) 게시물 등록 content를 form을 add하는 것만 있지 delete 하는 것은 없음
 * - (선택사항) 회원 가입에서 주소 입력시 정해진 형식대로 입력 안하면 회원가입 못하도록
 * - (선택사항) 비밀번호 확인(눈)가능
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
	
	// Best Of 
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
	public String bestOfDetail(Model model, @ModelAttribute PostDTO postDTO) { 
		// @RequestParam("postId") long postId, @RequestParam("sort") String sort으로 하면 bestOfDetail로 보내는 모든 부분에서 postId, sort를 포함하고 있어야 해서 한 번에 받는게 더 나음
		// Best Of를 BestOf로 바꿔야 url 이동 원활하게 가능
		model.addAttribute("postDTO", postService.getBestOfDetailPost(postDTO.getPostId()));
		model.addAttribute("contentDTOList", postService.getBestOfDetailContent(postDTO.getPostId()));
		model.addAttribute("contentImpactMsg", postService.getBestOfDetailContentImpactMsg(postDTO.getPostId()));		
		model.addAttribute("replyList", replyService.getReplyList(postDTO.getPostId()));
		model.addAttribute("replyCnt", replyService.getReplyCnt(postDTO.getPostId()));
		model.addAttribute("similarSortList", postService.getSimilarSortList(postDTO));
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
