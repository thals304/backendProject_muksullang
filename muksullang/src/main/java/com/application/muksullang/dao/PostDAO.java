package com.application.muksullang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.application.muksullang.dto.PostDTO;

@Mapper
public interface PostDAO {

	// Best Of인 게시물 정보 리스트 가져오기
	public List<PostDTO> getBestOfList(String type);
}
