package com.application.muksullang.service;

import java.util.List;
import java.util.Map;

import com.application.muksullang.dto.ContentDTO;
import com.application.muksullang.dto.PostDTO;

public interface PostService {

	// main 화면에 뿌릴 게시물 (Best Of)
	public List<PostDTO> getLeftBestOfPostList();
	public PostDTO getCenterBestOfPost();
	public List<PostDTO> getRightBestOfPostList();
	// main 화면에 뿌릴 게시물 (Recommend)
	public PostDTO getLeftRecommendPost();
	public List<PostDTO> getRightRecommendPostList();
	
	// (페이지네이션) 해당 타입 게시물 총 개수 조회
	public int getCountPosts(String type);
	// (페이지네이션) 현재 페이지에 해당하는 게시물 리스트 조회
	public List<PostDTO> getCurrentPagePostList(String type, int offset, int limit);
	
	// best of 게시물 전체 조회
	public List<PostDTO> getBestOfList(String type);
	
	// best Of에서 검색하기 기능
	public List<PostDTO> searchBestOfPost(Map<String,Object> params);
	
	// best of 디테일 (post 따로, content 따로)
	public PostDTO getBestOfDetailPost(long postId);
    public List<ContentDTO> getBestOfDetailContent(long postId);
	public ContentDTO getBestOfDetailContentImpactMsg(long postId);
	public List<PostDTO> getSimilarSortList(PostDTO postDTO);
	
	public List<PostDTO> getRecommendList(String type);
	public String getRecommendContent(long postId);
	
	// recommend 디테일 (post 따로, content 따로)
	public PostDTO getRecommendDetailPost(long postId);
	public List<ContentDTO> getRecommendDetailContent(long postId);
	
}
