package com.manhui.service;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.DayAllLog;

@Mapper
public interface DayAllLogService {
	
	//查询当天显示数据
	DayAllLog findDayAllLogByDate(Date addTime);
	
	//保存显示数据
	void saveDayAllLog(DayAllLog dal);
	
	//查询历史数据
	DayAllLog findDayAllLogHistory();
	
	//修改显示数据
	void updateDayAllLog(DayAllLog dal);
}
