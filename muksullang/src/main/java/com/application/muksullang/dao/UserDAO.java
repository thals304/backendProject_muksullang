package com.application.muksullang.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.muksullang.dto.PostDTO;
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
	
	// (페이지네이션) user 의 찜한 게시물 & 리뷰 쓴 게시물 총 개수
	public int getCountPosts(String userId);
	// (페이지네이션)user의 찜한 게시물 & 리뷰 쓴 게시물 현재 페이지에 해당하는 게시물 리스트 가져오기
	public List<PostDTO> getAllPosts(Map<String, Object> params);
	
	// user의 찜한 게시물 전체 모아보기
	public List<PostDTO> getBookmarkedPosts(String userId);
	
	// user의 리뷰 쓴 게시물 전체 모아보기
	public List<PostDTO> getReviewedPosts(String userId);
	
	// 유저 정보 수정
	public void updateUser(UserDTO userDTO);
	
	// 탈퇴 회원 activeYn = "n"으로 업데이트
	public void updateInactiveUser(String userId);
	
	// 신규 회원 수 count
	public int getTodayNewMemberCnt(String today);
	
	// 비활성화 회원 전체조회
	public List<UserDTO> getInActiveMemberList();
	
	// 비활성화 된지 60일 지난 회원 삭제
	public void deleteUser(String userId);
}
