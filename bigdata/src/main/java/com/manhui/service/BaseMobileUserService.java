package com.manhui.service;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.BaseMobileUser;

@Mapper
public interface BaseMobileUserService {
	
	//验证登录是否成功
	BaseMobileUser findUserByNameAndPassword(BaseMobileUser user);

	//根据id查询移动端登录信息
	BaseMobileUser findUserById(Integer id);
}
