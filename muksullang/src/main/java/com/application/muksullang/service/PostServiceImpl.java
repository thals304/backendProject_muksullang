package com.application.muksullang.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.muksullang.dao.PostDAO;
import com.application.muksullang.dto.ContentDTO;
import com.application.muksullang.dto.PostDTO;

@Service
public class PostServiceImpl implements PostService {
		
		@Autowired
		private PostDAO postDAO;

		@Override
		public List<PostDTO> getBestOfList(String type) {
			return postDAO.getBestOfList(type);
		}

		@Override
		public List<PostDTO> searchBestOfPost(Map<String, Object> params) {
			
			return postDAO.searchBestOfPost(params);
		}

		@Override
		public PostDTO getBestOfDetailPost(long postId) {
			
			return postDAO.getBestOfDetailPost(postId);
		}

		@Override
		public List<ContentDTO> getBestOfDetailContent(long postId) {
		
			return postDAO.getBestOfDetailContent(postId);
		}

		@Override
		public ContentDTO getBestOfDetailContentImpactMsg(long postId) {
	
			return postDAO.getBestOfDetailContentImpactMsg(postId);
		}

		@Override
		public List<PostDTO> getRecommendList(String type) {
			return postDAO.getRecommendList(type);
		}

		
}
