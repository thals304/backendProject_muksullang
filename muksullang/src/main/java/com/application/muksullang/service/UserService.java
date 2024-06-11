package com.application.muksullang.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.application.muksullang.dto.UserDTO;

public interface UserService {

	public void signUp(UserDTO userDTO);
	
	public String checkValidId(String userId);
	
	public boolean signIn(UserDTO userDTO);
	
	public UserDTO getUserDetail(String userId);
	
	public void updateUser(MultipartFile uploadProfile, UserDTO userDTO) throws IllegalStateException, IOException;
	
	public void updateInactiveUser(String userId);
}
