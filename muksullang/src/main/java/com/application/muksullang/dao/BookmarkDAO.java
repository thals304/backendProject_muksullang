package com.application.muksullang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.application.muksullang.dto.BookmarkDTO;

@Mapper
public interface BookmarkDAO {

	public Integer existsByUserIdAndPostId(BookmarkDTO bookmarkDTO);
	
	public void addBookmark(BookmarkDTO bookmarkDTO);
	
	public void deleteBookmark(BookmarkDTO bookmarkDTO);
	
	public List<Integer> getBookmarkedPostIds(String userId);
}
