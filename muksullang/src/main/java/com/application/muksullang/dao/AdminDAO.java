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
}
