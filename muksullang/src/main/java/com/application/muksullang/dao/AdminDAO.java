package com.application.muksullang.dao;

import org.apache.ibatis.annotations.Mapper;

import com.application.muksullang.dto.AdminDTO;

@Mapper
public interface AdminDAO {

	public AdminDTO signIn(AdminDTO adminDTO);
}
