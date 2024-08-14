package com.application.muksullang.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.muksullang.dao.AdminDAO;
import com.application.muksullang.dto.AdminDTO;
import com.application.muksullang.dto.ContentDTO;
import com.application.muksullang.dto.PostDTO;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Value("${image.repo.path}")
	private String imageRepositoryPath;
	
	@Autowired
	private AdminDAO adminDAO;

	@Override
	public AdminDTO signIn(AdminDTO adminDTO) {
	
		return adminDAO.signIn(adminDTO);
	}

	@Override
	public void createPost(MultipartFile uploadImage, PostDTO postDTO) throws IllegalStateException, IOException {
		
		if(!uploadImage.isEmpty()) {
			// 게시물 사진은 uuid 안할건데 그냥 transferTo하면 되나?
			String originalFilename = uploadImage.getOriginalFilename();
			postDTO.setImage(originalFilename);
			
			uploadImage.transferTo(new File(imageRepositoryPath + originalFilename));
		}
		adminDAO.createPost(postDTO);
		// 삽입된 postId 출력 (확인용)
	    System.out.println("Inserted Post ID: " + postDTO.getPostId());
	}

	@Override
	public void createContent(List<ContentDTO> contentList) {
		
		    for (ContentDTO dto : contentList) {
		        adminDAO.createContent(dto); // 각 contentDTO를 생성하는 DAO 호출
		    }
		
	}

	@Override
	public int getUserCnt() {
		return adminDAO.getUserCnt();
	}

	@Override
	public int getBestOfPostCnt() {
		return adminDAO.getBestOfPostCnt();
	}
	
	@Override
	public int getRecommendPostCnt() {
		return adminDAO.getRecommendPostCnt();
	}

	@Override
	public int getReplyCnt() {
		return adminDAO.getReplyCnt();
	}

	@Override
	public List<ContentDTO> getBestOfDetailContent(long postId) {
		return adminDAO.getBestOfDetailContent(postId);
	}

	@Override
	public void updateBestOfPost(PostDTO postDTO, MultipartFile uploadImage) throws IllegalStateException, IOException {
		if(!uploadImage.isEmpty()) {
			String originalFileName = uploadImage.getOriginalFilename();
			
			// c파일에 있는 기존 파일 삭제 후 다시 등록해야함
			new File(imageRepositoryPath + postDTO.getImage()).delete();
			
			postDTO.setImage(originalFileName);
			
			uploadImage.transferTo(new File(imageRepositoryPath + originalFileName));
		}
		
		adminDAO.updateBestOfPost(postDTO);
		
		List<ContentDTO> contentList = postDTO.getContentList();
		for (ContentDTO contentDTO : contentList) {
			adminDAO.updateBestOfContent(contentDTO);
		}
		
	}

	@Override
	public List<ContentDTO> getRecommendDetailContent(long postId) {
		return adminDAO.getRecommendDetailContent(postId);
	}
	
	@Override
	public void updateRecommendPost(PostDTO postDTO, MultipartFile uploadImage) throws IllegalStateException, IOException {
		if(!uploadImage.isEmpty()) {
			
			String originalFileName = uploadImage.getOriginalFilename();
			
			new File(imageRepositoryPath + postDTO.getImage()).delete();
			
			postDTO.setImage(originalFileName);
			
			uploadImage.transferTo(new File(imageRepositoryPath + originalFileName));
			
		}
		
		adminDAO.updateRecommendPost(postDTO);
		
		List<ContentDTO> contentDTOList = postDTO.getContentList();
		for (ContentDTO contentDTO : contentDTOList) {
			adminDAO.updateRecommendContent(contentDTO);
		}
	}
	
	@Override
	public void deletePost(long postId) {
		adminDAO.deletePost(postId);
	}

	


}
