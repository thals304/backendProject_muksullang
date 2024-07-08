package com.application.muksullang.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.muksullang.dto.ContentDTO;
import com.application.muksullang.dto.PostDTO;

@Mapper
public interface PostDAO {

	// 이달의 Best Of 게시물
	public List<PostDTO> getLeftBestOfPostList(); // 왼쪽 2개
	public PostDTO getCenterBestOfPost(); // 중간 1개
	public List<PostDTO> getRightBestOfPostList(); // 오른쪽 1개
	
	// 이달의 Recommend 게시물
	public PostDTO getLeftRecommendPost(); // 왼쪽 1개
	public List<PostDTO> getRightRecommendPostList(); // 오른쪽 4개
	
	// Best Of인 게시물 정보 리스트 가져오기
	public List<PostDTO> getBestOfList(String type);
	
	// Best Of에서 검색한 게시물 리스트 가져오기
	public List<PostDTO> searchBestOfPost(Map<String, Object> searchBestOfPost);

	// Best Of의 게시물 디테일 정보 (POST)
	public PostDTO getBestOfDetailPost(long postId);
	// Best Of의 게시물 디테일 정보 (CONTENT - 가게 소개, 메뉴 소개 및 추천)
	public List<ContentDTO> getBestOfDetailContent(long postId);
	// Best Of의 게시물 디테일 정보 (CONTENT - 가게에 대한 임팩트 메시지)
	public ContentDTO getBestOfDetailContentImpactMsg(long postId);
	// Best Of의 게시물 디테일 정보 > 유사한 sort 3개 뽑아 추천
	public List<PostDTO> getSimilarSortList(PostDTO postDTO);
	
	// Recommend인 게시물 정보 리스트 가져오기
	public List<PostDTO> getRecommendList(String type);
	// Recommend인 게시물에 소개 내용 1개 가져오기
	public String getRecommendContent(long postId); 
	
	// Recommend의 게시물 디테일 정보 (POST)
	public PostDTO getRecommendDetailPost(long postId);
}
