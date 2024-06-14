package com.application.muksullang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.muksullang.dao.AdminDAO;
import com.application.muksullang.dto.AdminDTO;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminDAO adminDAO;

	@Override
	public AdminDTO signIn(AdminDTO adminDTO) {
	
		return adminDAO.signIn(adminDTO);
	}

}
