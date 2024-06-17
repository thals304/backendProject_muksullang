package com.application.muksullang.dto;


import lombok.Data;

@Data
public class PostDTO {
	
	private Integer   postId;
	private String postNm;
	private String adminId;
	private String title;
	private String sort;
	private String location;
	private String image;
	private String runningTime;
	private String enrollAt;
	
}
