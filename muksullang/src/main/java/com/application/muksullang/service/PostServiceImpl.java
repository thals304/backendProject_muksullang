package com.application.muksullang.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

		@Override
		public String getRecommendContent(long postId) {
			return postDAO.getRecommendContent(postId);
		}

		@Override
		public PostDTO getRecommendDetailPost(long postId) {
			return postDAO.getRecommendDetailPost(postId);
		}

		@Override
		public List<PostDTO> getSimilarSortList(PostDTO postDTO) {
			return postDAO.getSimilarSortList(postDTO);
		}


		
}
