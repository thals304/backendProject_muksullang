package com.application.muksullang.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.muksullang.dao.ReplyDAO;
import com.application.muksullang.dto.ReplyDTO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDAO replyDAO;

	@Override
	public List<Map<String,Object>> getReplyList(long postId) {
		return replyDAO.getReplyList(postId);
	}

	@Override
	public int getReplyCnt(long postId) {
		return replyDAO.getReplyCnt(postId);
	}

	@Override
	public void creatReply(ReplyDTO replyDTO) {
		replyDAO.creatReply(replyDTO);
	}

	@Override
	public ReplyDTO getReplyDetail(long replyId) {
		return replyDAO.getReplyDetail(replyId);
	}

	@Override
	public void updateReply(ReplyDTO replyDTO) {
		replyDAO.updateReply(replyDTO);
	}

	@Override
	public void deleteReply(long replyId) {
		replyDAO.deleteReply(replyId);
	}
}
