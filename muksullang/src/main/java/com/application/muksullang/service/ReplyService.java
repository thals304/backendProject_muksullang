package com.application.muksullang.service;

import java.util.List;
import java.util.Map;

import com.application.muksullang.dto.ReplyDTO;

public interface ReplyService {

	public List<Map<String, Object>> getReplyList(long postId);
	public int getReplyCnt(long postId);
	
	public void creatReply(ReplyDTO replyDTO);
	
	public ReplyDTO getReplyDetail(long replyId);
}
