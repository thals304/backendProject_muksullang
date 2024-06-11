package com.application.muksullang.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.muksullang.dao.UserDAO;
import com.application.muksullang.dto.UserDTO;

@Service
public class UserServiceImpl implements UserService {

	@Value("${file.repo.path}")
	private String fileRepositoryPath;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

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
	public void updateUser(MultipartFile uploadProfile, UserDTO userDTO) throws IllegalStateException, IOException {
		
		if(!uploadProfile.isEmpty()) {
			// 기존 default인 profile을 삭제하면 다른 유저들 기본 파일 이미지도 없어지는 것인데 근데 또 다른 이미지 업데이트 했다가 또 수정할때는 삭제해줘야 하는데
			// userDTO.getProfileUUID()가 userProfile.PNG가 아닌 경우만 C드라이브에서 삭제해주면 되지 않을까?
			if(!userDTO.getProfileUUID().equals("userProfile.PNG")){
				new File(fileRepositoryPath + userDTO.getProfileUUID()).delete();
			}
			
			String originalFilename = uploadProfile.getOriginalFilename();
			userDTO.setProfileOriginalName(originalFilename);
			
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			
			String uploadFile = UUID.randomUUID() + extension;
			userDTO.setProfileUUID(uploadFile);
			
			uploadProfile.transferTo(new File(fileRepositoryPath + uploadFile));
			
		}
		
		// 비밀번호 수정한 것도 암호화 필요 -> 만약 비밀번호 수정을 안하면 null로 넘어오나? 제한 걸어줘야 하나?
		if (userDTO.getPasswd() != null) {
			userDTO.setPasswd(passwordEncoder.encode(userDTO.getPasswd()));
		}
		
		userDAO.updateUser(userDTO);
		
	}

	@Override
	public void updateInactiveUser(String userId) {
		
		userDAO.updateInactiveUser(userId);
	}

	
}
