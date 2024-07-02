package com.application.muksullang.dto;


import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PostDTO {
	
	private long   postId;
	private String postNm;
	private String adminId;
	private String title;
	private String sort;
	private String location;
	private String image;
	private String runningTime;
	private Date enrollAt;
	
	private List<ContentDTO> contentList;
}
