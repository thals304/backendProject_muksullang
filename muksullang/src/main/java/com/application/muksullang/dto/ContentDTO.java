package com.application.muksullang.dto;

import java.util.List;

import lombok.Data;

@Data
public class ContentDTO {
	
	private long   contentId;
	private String content;
	private long   postId;
	
	private List<ContentDTO> contentDTOList;
}
