package com.application.muksullang.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.muksullang.dao.UserDAO;
import com.application.muksullang.dto.PostDTO;
import com.application.muksullang.dto.UserDTO;

@Service
public class UserServiceImpl implements UserService {

	@Value("${file.repo.path}")
	private String fileRepositoryPath;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	

	@Override
	public void signUp(UserDTO userDTO) {
		
		// 프로필 사진은 기본값으로 db에 알아서 저장되니 회원가입에서는 원본 파일, UUID 할 필요 없음
		
		// agreeYn은 반드시 동의해야 하는 것이므로 이메일 수신동의처럼 할 필요 없음
		if (userDTO.getAgreeEmailYn() == null) userDTO.setAgreeEmailYn("n");
		
		userDTO.setPasswd(passwordEncoder.encode(userDTO.getPasswd()));
		
		userDAO.signUp(userDTO);
		
	}
	
	
	@Override
	public String checkValidId(String userId) {
		
		String isValid = "n";
		
		if(userDAO.checkValidId(userId) == null) { // 중복되는 아이디 존재 x
			isValid = "y";
		}
		
		return isValid;
	}


	@Override
	public boolean signIn(UserDTO userDTO) {
		
		UserDTO resultDTO = userDAO.signIn(userDTO.getUserId());
		
		if (resultDTO != null) {
			if (passwordEncoder.matches(userDTO.getPasswd(), resultDTO.getPasswd()) && resultDTO.getActiveYn().equals("y")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public UserDTO getUserDetail(String userId) {
		return userDAO.getUserDetail(userId);
	}
	
	@Override
	public List<PostDTO> getBookmarkedPosts(String userId) {
		return userDAO.getBookmarkedPosts(userId);
	}
	
	
	@Override
	public List<PostDTO> getReviewedPosts(String userId) {
		return userDAO.getReviewedPosts(userId);
	}

	@Override
	public void updateUser(MultipartFile uploadProfile, UserDTO userDTO) throws IllegalStateException, IOException {
		
		if(!uploadProfile.isEmpty()) {
			// 기존 default인 profile을 삭제하면 다른 유저들 기본 파일 이미지도 없어지는 것인데 근데 또 다른 이미지 업데이트 했다가 또 수정할때는 삭제해줘야 하는데
			String originalFilename = uploadProfile.getOriginalFilename();
			
			// 이렇게 하면 userProfile.png가 삭제됨 > 기존 UUID가 userProfile.png와 다를 때 삭제하는 조건 추가
			if(!originalFilename.equals("userProfile.png")){
				if (!userDTO.getProfileUUID().equals("userProfile.png")) {
					new File(fileRepositoryPath + userDTO.getProfileUUID()).delete();
				}
			}
			
			userDTO.setProfileOriginalName(originalFilename);
			
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			
			String uploadFile = UUID.randomUUID() + extension;
			userDTO.setProfileUUID(uploadFile);
			
			uploadProfile.transferTo(new File(fileRepositoryPath + uploadFile));
			
		}
		
		// 비밀번호 수정한 것도 암호화 필요 -> 만약 비밀번호 수정을 안하면 null로 넘어오나? 
		// no (그래도 null이 아닐 경우에만 암호화 하도록 if 조건 걸어주는 것이 좋을 것 같음)
		if (userDTO.getPasswd() != null) {
			userDTO.setPasswd(passwordEncoder.encode(userDTO.getPasswd()));
		}
		
		userDAO.updateUser(userDTO);
		
	}

	@Override
	public void updateInactiveUser(String userId) {
		
		userDAO.updateInactiveUser(userId);
	}

	@Override
	public List<PostDTO> getAllPosts(String userId) {
		return userDAO.getAllPosts(userId);
	}
	
	
	@Override
	@Scheduled(cron="59 59 23 * * *")
	public void getTodayNewMemberCnt() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(new Date());
		
		logger.info("(" + today + ") 신규회원수 : " + userDAO.getTodayNewMemberCnt(today));
		
	}

	@Override
	@Scheduled(cron="59 59 23 * * *")
	public void deleteMemberScheduler() { // INACTIVE_AT이 60일 지난 회원 삭제
		
		List<UserDTO> deleteMemberList = userDAO.getInActiveMemberList();
		
		if(!deleteMemberList.isEmpty()) {
			for (UserDTO userDTO : deleteMemberList) {
				if(!userDTO.getProfileUUID().equals("userProfile.png")) {
					new File(fileRepositoryPath + userDTO.getProfileUUID()).delete();
				}
				userDAO.deleteUser(userDTO.getUserId());
			}
		}
		
	}





	
}
