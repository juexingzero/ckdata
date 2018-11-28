package com.manhui.model;

import java.util.List;

/**
 * @ClassName: BaseUser
 * @description:基础用户表
 * @author: Jiangxiaosong
 * @date Create in 15:29 2018/1/28
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class BaseUser {
    private Integer id;

    private String username;

    private String password;

    private String salt;
    
    private Integer state;
    
    private List<BaseRole> baseRoleList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public List<BaseRole> getBaseRoleList() {
		return baseRoleList;
	}

	public void setBaseRoleList(List<BaseRole> baseRoleList) {
		this.baseRoleList = baseRoleList;
	}
}