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
	
	// 스케쥴링 - 신규 회원 조회
	public void getTodayNewMemberCnt();
	
	// 스케쥴링 - 비활성화 회원 조회 및 삭제
	public void deleteMemberScheduler();
}
