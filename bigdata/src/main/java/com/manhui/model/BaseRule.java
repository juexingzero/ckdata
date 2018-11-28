package com.manhui.model;

/**
 * @ClassName: BaseRule
 * @description: 基础规则类(用户角色关联类)
 * @author: Jiangxiaosong
 * @date Create in 15:29 2018/1/27
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class BaseRule {
	
	 private Integer id;
	
	 private Integer userId;    //用户id
	
	 private String username;   //用户名称
	 
	 private String password;   //用户密码

	 private Integer roleId;    //角色编码
	
	 private String name;       //角色名称
	
	 private String descr;      //描述
	 
	 private Integer userState; //用户状态

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}
	 
}
