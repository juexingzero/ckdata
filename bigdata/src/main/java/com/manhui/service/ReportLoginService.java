package com.manhui.service;


import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.ReportLogin;

@Mapper
public interface ReportLoginService {

	//保存登录信息
	void saveReportLogin(ReportLogin rl);
	
	//根据时间顺序查询登录信息
	ReportLogin findReportLoginOrderByTime(Integer userId);
}
