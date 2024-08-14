package com.application.muksullang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.muksullang.dao.BookmarkDAO;
import com.application.muksullang.dto.BookmarkDTO;

@Service
public class BookmarkServiceImpl implements BookmarkService {

	@Autowired
	private BookmarkDAO bookmarkDAO;

	
	@Override
	public boolean existsByUserIdAndPostId(BookmarkDTO bookmarkDTO) {
		  
		/*
		 * true: 북마크가 존재하는 경우 (즉, count가 1 이상일 때).
		   false: 북마크가 존재하지 않는 경우 (즉, count가 0일 때).
		 * */
		  Integer count = bookmarkDAO.existsByUserIdAndPostId(bookmarkDTO);
		  
		  return count != null && count > 0;
	}
	
	
	@Override
	public void addBookMark(BookmarkDTO bookmarkDTO) {
		bookmarkDAO.addBookmark(bookmarkDTO);
		
	}

	@Override
	public void deleteBookMark(BookmarkDTO bookmarkDTO) {
		bookmarkDAO.deleteBookmark(bookmarkDTO);
		
	}


	@Override
	public List<Integer> getBookmarkedPostIds(String userId) {
		return bookmarkDAO.getBookmarkedPostIds(userId);
	}
	}
