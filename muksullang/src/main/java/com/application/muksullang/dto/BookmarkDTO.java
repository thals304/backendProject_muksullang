package com.application.muksullang.dto;

import java.util.Date;

import lombok.Data;

@Data
public class BookmarkDTO {

	private long   bookmarkId;
	private String userId;
	private long   postId;
	private Date   regDt;
	
	private String action;
}
