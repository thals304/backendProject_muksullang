package com.application.muksullang.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AdminDTO {

	private String adminId;
	private String adminPasswd;
	private String role;
	private Date   joinAt;
}
