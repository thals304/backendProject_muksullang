package com.application.muksullang.dao;

import org.apache.ibatis.annotations.Mapper;

import com.application.muksullang.dto.UserDTO;

@Mapper
public interface UserDAO {

	// 회원가입
	public void signUp(UserDTO userDTO);
	
	// 아이디 중복 체크
	public String checkValidId(String userId);
	
	// 로그인
	public UserDTO signIn(String userId);
	
	// 유저 디테일 정보 가져오기
	public UserDTO getUserDetail(String userId);
	
	// 유저 정보 수정
	public void updateUser(UserDTO userDTO);
	
	// 탈퇴 회원 activeYn = "n"으로 업데이트
	public void updateInactiveUser(String userId);
	
}
