package com.application.muksullang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.muksullang.dao.PostDAO;

@Service
public class PostServiceImpl implements PostService {
		
		@Autowired
		private PostDAO postDAO;
}
