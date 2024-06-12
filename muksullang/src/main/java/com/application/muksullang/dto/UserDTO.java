package com.application.muksullang.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserDTO {

	private String userId;
	private String passwd;
	private String email;
	private String address;
	private String profileOriginalName;
	private String profileUUID;
	private String agreeYn;
	private String agreeEmailYn;
	private String activeYn;
	private Date   inactiveAt;
	private Date   joinAt;
	private String role;
	
}
