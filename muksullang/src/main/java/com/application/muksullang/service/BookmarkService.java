package com.application.muksullang.service;

import java.util.List;

import com.application.muksullang.dto.BookmarkDTO;

public interface BookmarkService {

	// 북마크 존재 여부
	public boolean existsByUserIdAndPostId(BookmarkDTO bookmarkDTO);
	
	// 게시물 찜하기
	public void addBookMark(BookmarkDTO bookmarkDTO);
	
	// 게시물 찜하기 취소
	public void deleteBookMark(BookmarkDTO bookmarkDTO);
	
	// 찜한 게시물 리스트 조회하기
	public List<Integer> getBookmarkedPostIds(String userId);
	
}
