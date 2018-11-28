package com.manhui.model;

import java.util.Date;

public class ReportLogin {
    private Integer id;
    
    private Integer mobileUserId;

    private String name;

    private String password;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMobileUserId() {
		return mobileUserId;
	}

	public void setMobileUserId(Integer mobileUserId) {
		this.mobileUserId = mobileUserId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}