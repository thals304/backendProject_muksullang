package com.application.muksullang.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyDTO {

	private long replyId;
	private long postId;
	private String userId;
	private int rating;
	private String content;
	private Date uploadDt;
}
