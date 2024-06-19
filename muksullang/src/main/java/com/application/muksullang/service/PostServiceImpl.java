package com.application.muksullang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.muksullang.dao.PostDAO;
import com.application.muksullang.dto.PostDTO;

@Service
public class PostServiceImpl implements PostService {
		
		@Autowired
		private PostDAO postDAO;

		@Override
		public List<PostDTO> getBestOfList(String type) {
			
			return postDAO.getBestOfList(type);
		}
}
