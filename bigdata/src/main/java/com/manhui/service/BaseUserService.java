package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.BaseRule;
import com.manhui.model.BaseUser;

/**
 * @ClassName: BaseUserService
 * @description: 基础用户 Service 接口
 * @author: Jiangxiaosong
 * @date Create in 15:29 2018/1/25
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface BaseUserService {

	//登录用查询用户名，密码
	BaseUser findByUserAndPassword(BaseUser baseUser);
	
    //提供用户名模糊查询用户数据
	List<BaseUser> findByBaseUserName(String Name);
	
	//根据角色id查询用户数据
	List<BaseUser> findByRoleId(Integer roleId);
	
	//根据用户id查询用户数据
	BaseUser findByUserId(Integer userId);
	
	//查询所有可用用户
	List<BaseUser> findBaseUser();
	
	//查找最大用户id
	int findBaseUserMaxId();
 	
	//更新用户信息
	void updateBaseUser(BaseUser baseUser);

	//新增用户信息
	void insertBaseUser(BaseUser baseUser);
	
	//删除用户信息
	void deleteBaseUser(Integer id);
}