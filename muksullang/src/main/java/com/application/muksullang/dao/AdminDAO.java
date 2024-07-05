package com.application.muksullang.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.application.muksullang.dto.AdminDTO;
import com.application.muksullang.dto.ContentDTO;
import com.application.muksullang.dto.PostDTO;

@Mapper
public interface AdminDAO {

	// admin 로그인
	public AdminDTO signIn(AdminDTO adminDTO);
	
	// 게시물 등록 (content 빼고) 
	public void createPost(PostDTO postDTO);
	
	// 게시물의 content 등록
	public void createContent(ContentDTO contentDTO);
	
	// 유저 수 카운트
	public int getUserCnt();
	// Best Of 게시물 수 카운트
	public int getBestOfPostCnt();
	// Recommend 게시물 수 카운트
	public int getRecommendPostCnt();
	// 리뷰 수 카운트
	public int getReplyCnt();
	
	// bestOf content 조회하기
	public List<ContentDTO> getBestOfDetailContent(long postId);
	
	// bestOf Post 업데이트
	public void updateBestOfPost(PostDTO postDTO);
	// bestOf Content 업데이트
	public void updateBestOfContent(ContentDTO contentDTO);
	
	// bestOf 게시물 삭제
	public void deleteBestOfPost(long postId);
	
}
