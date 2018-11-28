package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.BaseRole;
import com.manhui.model.BaseUser;

/**
 * @ClassName: BaseRoleService
 * @description: 基础角色 Service 接口
 * @author: Jiangxiaosong
 * @date Create in 15:29 2018/1/26
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface BaseRoleService {
	
	//根据名称查询基础角色(可模糊查询)
	List<BaseRole> findRoleByName(String name);
	
	//根据用户ID查询基础角色
	List<BaseRole> findRoleByUserId(Integer UserId);
	
	//查询所有角色
	List<BaseRole> findBaseRole();
	
	//修改基础角色信息
	void updateBaseRole(BaseRole role);
	
	//新增基础角色信息
	void insertBaseRole(BaseRole role);
	
	//删除基础角色信息
	void deleteBaseRoleById(Integer id);
}