package com.application.muksullang.controller;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.muksullang.dto.BookmarkDTO;
import com.application.muksullang.dto.ContentDTO;
import com.application.muksullang.dto.PostDTO;
import com.application.muksullang.dto.ReplyDTO;
import com.application.muksullang.service.BookmarkService;
import com.application.muksullang.service.PostService;
import com.application.muksullang.service.ReplyService;

import jakarta.servlet.http.HttpServletRequest;

/*
 *  
 *  //(중요) mapper에서 resultMap -> hashmap을 사용해서 매핑하려고 한다면, JOIN을 해서 SELECT하고 싶은 컬럼들이 resultMap 요소에 다 들어가 있어야 사용 가능 
 * 
 * 게시물 내용 textarea에서 br \n 적용이 안되는 고민 생김 > ckeditor 또는 utext로 해서 textarea에서 <br> 넣기
 * 
 * bestOfDetail에서 similarSortList가 원활하게 작동을 안함
 * aop 공부 후 사용해봐야함/test 코드도
 * 프로젝트 구현 안한 부분
 * - 북마크(게시물 찜하기) : bestOf/recommend.html에서 북마크 버튼 생성이 눌러져서 저장은 되지만 눌러진 하트가 고정이 되지 않음 !(좀 더 연구 필요)
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
	
	@Autowired
	private BookmarkService bookmarkService;
	
	// 웹페이지 첫 화면 main 
	@GetMapping("/main")
	public String main(Model model) {
		// 이달의 Best Of : 왼쪽 2개 중간 1개 오른쪽 2개 (일단 enroll 기준으로 하고 나중에 찜하기 기능 완성하면 찜하기 수가 많거나 리뷰수가 많은 게시물을 뿌리는 방향으로)
		// 이달의 Recommend : 왼쪽 1개 오른쪽 3개
		List<PostDTO> leftBestOfPostList = postService.getLeftBestOfPostList();
	    PostDTO centerBestOfPost = postService.getCenterBestOfPost();
	    List<PostDTO> rightBestOfPostList = postService.getRightBestOfPostList();
	    
	    // 데이터가 없는 경우 (게시물이 하나도 없을 경우 500 에러 발생)
	    if (leftBestOfPostList == null || leftBestOfPostList.isEmpty()) {
	        throw new com.application.muksullang.except.ServerErrorException("왼쪽 게시물을 찾을 수 없습니다.");
	    }

	    if (centerBestOfPost == null) {
	        throw new com.application.muksullang.except.ServerErrorException("중앙 게시물을 찾을 수 없습니다.");
	    }

	    if (rightBestOfPostList == null || rightBestOfPostList.isEmpty()) {
	        throw new com.application.muksullang.except.ServerErrorException("오른쪽 게시물을 찾을 수 없습니다.");
	    }
		
		
	    // Recommend 처리
	    PostDTO leftRecommendPostDTO = postService.getLeftRecommendPost();
	    if (leftRecommendPostDTO == null) {
	        throw new com.application.muksullang.except.ServerErrorException("추천 게시물을 찾을 수 없습니다.");
	    }

	    List<PostDTO> rightRecommendPostList = postService.getRightRecommendPostList();
	    if (rightRecommendPostList == null || rightRecommendPostList.isEmpty()) {
	        throw new com.application.muksullang.except.ServerErrorException("추천 게시물을 찾을 수 없습니다.");
	    }
	    
	    // 데이터가 있으면 모델에 추가
	    model.addAttribute("leftBestOfPostList", leftBestOfPostList);
	    model.addAttribute("centerBestOfPost", centerBestOfPost);
	    model.addAttribute("rightBestOfPostList", rightBestOfPostList);
	    model.addAttribute("leftRecommendPost", leftRecommendPostDTO);
	    model.addAttribute("leftRecommendPostContent", postService.getRecommendContent(leftRecommendPostDTO.getPostId()));
	    model.addAttribute("rightRecommendPostList", rightRecommendPostList);
		
		return "post/main";
	}
	
	// Best Of 
	@GetMapping("/bestOf")
	public String bestOf(@RequestParam(value = "page", defaultValue = "1") int page, HttpServletRequest request ,Model model) {
		// 게시물 수가 9개 이상일 때 다음 페이지로 넘어가는 페이지네이션 기능을 만들어야 함
		
		int pageSize = 9; // 페이지당 게시물 수
		
		String type = "BestOf";
		
		// 가져올 게시물의 시작 위치를 계산
		// 현재 페이지에서 몇 번째 데이터부터 가져올지를 결정하는 역할
		// offset =  (현재 페이지 번호 - 1) * 페이지당 보여줄 게시물 수
	    int offset = (page - 1) * pageSize;
		
	    // 총 게시물 수를 가져와서 페이지 수 계산
	    int totalPosts = postService.getCountPosts(type);
	    int totalPages = (int) Math.ceil((double) totalPosts / pageSize );
	    
	    // 현재 페이지에 해당하는 게시물 리스트 가져오기
	    List<PostDTO> bestOfList = postService.getCurrentPagePostList(type, offset, pageSize);
		model.addAttribute("bestOfList", bestOfList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		
		// 세션에서 userId 가져오기
		String userId = (String)request.getSession().getAttribute("userId");
		
		if (userId != null) {
			// userId가 세션에 저장되어 있는 경우
	        // 찜한 게시물 ID 리스트 가져오기
	        List<Integer> bookmarkedPostIds = bookmarkService.getBookmarkedPostIds(userId);
	        model.addAttribute("bookmarkedPostIds", bookmarkedPostIds);
		}
		
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
	public String recommend(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		
		int pageSize = 5;
		
		String type = "Recommend";
		
		int offset = (page - 1) * pageSize;
		
		int totalPosts = postService.getCountPosts(type);
		int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
		
		List<PostDTO> recommendList = postService.getCurrentPagePostList(type, offset, pageSize);
		model.addAttribute("recommendList", recommendList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		
		// 각 게시물의 postId를 가지고 content 1개도 같이 html에 보내야함
		for(PostDTO postDTO : recommendList) {
			Long postId = postDTO.getPostId();
			String content = postService.getRecommendContent(postId);
			model.addAttribute("content_" + postId, content); // content_${postId}로 템플릿에서 사용 가능
			model.addAttribute("replyCnt", replyService.getReplyCnt(postId));
		}
		model.addAttribute("recommendList", recommendList);
		
		return "post/recommend";
	}
	
	// Recommend Detail
	@GetMapping("/recommedDetail")
	public String recommendDetail(Model model, @RequestParam("postId") long postId) {
		
		model.addAttribute("postDTO", postService.getRecommendDetailPost(postId));
		// html에서 th:each로 하는 것이 아닌 하나씩 content를 적용해주기 위해 content를 구분해서 보내줘야 될 것 같은데
		List<ContentDTO> contentDTOList = postService.getRecommendDetailContent(postId);
		int index = 1;
		for (ContentDTO contentDTO : contentDTOList) {
			model.addAttribute("content_" + index, contentDTO);
			index ++;
		}
		model.addAttribute("replyList", replyService.getReplyList(postId));
		model.addAttribute("replyCnt", replyService.getReplyCnt(postId));
		
		return "post/recommendDetail";
	}
	
	// About
	@GetMapping("/about")
	public String about() {
		return "post/about";
	}
	
	// Bookmark
	@PostMapping("/bookmark")
	@ResponseBody
	public String bookMarkPost(@RequestBody BookmarkDTO bookmarkDTO){
		String action = bookmarkDTO.getAction();
		boolean success = false;
		// userId, postId가 잘 넘어오는지 확인
		System.out.println("userID : " + bookmarkDTO.getUserId());
		System.out.println("postId : " + bookmarkDTO.getPostId());
		
		if(action.equals("add")) {
			if (!bookmarkService.existsByUserIdAndPostId(bookmarkDTO)) { // 북마크에 존재하지 않으면 추가
	            bookmarkService.addBookMark(bookmarkDTO);
	            success = true;
	        }
		}
		else if(action.equals("delete")) {
			if (bookmarkService.existsByUserIdAndPostId(bookmarkDTO)) { // 북마크에 존재하면 삭제
	            bookmarkService.deleteBookMark(bookmarkDTO);
	            success = true;
	        }
		}
		
		return success ? "y" : "n";
	}
}
