package com.application.muksullang.dto;

import java.util.List;

import lombok.Data;

@Data
public class ContentDTO {
	
	private String content;
	private long   postId;
	
	private List<ContentDTO> contentDTOList;
}
