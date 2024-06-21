package com.application.muksullang.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.muksullang.dto.ReplyDTO;

@Mapper
public interface ReplyDAO {

	// 1개의 게시글의 전체 댓글 조회
	public List<Map<String,Object>> getReplyList(long postId);
	
	// 1개의 게시글의 전체 댓글 개수 조회
	public int getReplyCnt(long postId);
	
	// 리뷰 등록
	public void creatReply(ReplyDTO replyDTO);
	
	// 1개의 댓글 정보 상세 조회
	public ReplyDTO getReplyDetail(long replyId);
}
