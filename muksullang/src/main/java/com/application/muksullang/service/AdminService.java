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

}
