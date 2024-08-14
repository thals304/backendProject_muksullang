package com.application.muksullang.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.application.muksullang.dto.AdminDTO;
import com.application.muksullang.dto.ContentDTO;
import com.application.muksullang.dto.PostDTO;

public interface AdminService {

	public AdminDTO signIn(AdminDTO adminDTO);
	
	public void createPost(MultipartFile uploadImage, PostDTO postDTO) throws IllegalStateException, IOException;

	public void createContent(List<ContentDTO> contentList);
	
	public int getUserCnt();
	
	public int getBestOfPostCnt();
	
	public int getRecommendPostCnt();
	
	public int getReplyCnt();
	
	public List<ContentDTO> getBestOfDetailContent(long postId);
	
	public void updateBestOfPost(PostDTO postDTO, MultipartFile uploadImage) throws IllegalStateException, IOException;
	
	public List<ContentDTO> getRecommendDetailContent(long postId);
	
	public void updateRecommendPost(PostDTO postDTO, MultipartFile uploadImage) throws IllegalStateException, IOException;

	public void deletePost(long postId);
}
