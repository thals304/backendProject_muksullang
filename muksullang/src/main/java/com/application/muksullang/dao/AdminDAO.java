package com.application.muksullang.dao;


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
	
	
}
