package com.application.muksullang.service;

import java.util.List;

import com.application.muksullang.dto.PostDTO;

public interface PostService {

	public List<PostDTO> getBestOfList(String type);
	
	
}
