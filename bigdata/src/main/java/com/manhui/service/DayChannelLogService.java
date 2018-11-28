package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.DayChannelLog;

/**
 * @ClassName: DayChannelLogService
 * @description: 渠道 Service 接口
 * @author: WangSheng
 * @date Create in 09:29 2018/1/31
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface DayChannelLogService {
	
	List<DayChannelLog> findDayCL(String time);
	
	//获取销售总额额和店铺数
	List<DayChannelLog> findAllDayChannelLog();
	
	//获取平台销售额
	List<DayChannelLog> findDayChannelLog();
	
	//获取平台店铺数
	List<DayChannelLog> findDayCh();
	
	//保存平台数据
	void insertDayChannelLog(DayChannelLog dclx);
}