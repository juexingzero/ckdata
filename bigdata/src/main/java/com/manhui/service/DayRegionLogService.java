package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.DayRegionLog;

/**
 * @ClassName: DayRegionLogService
 * @description: 地区销量Service 接口
 * @author: Jiangxiaosong
 * @date Create in 13:29 2018/3/12
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface DayRegionLogService {
	
	//地区销售排行
	List<DayRegionLog> findDayRegionLog();
	
	//销售地区热度
	List<DayRegionLog> findDayRegionLogByo();
	
	//保存销售地区日报
	void insertDayRegionLog(DayRegionLog drl);

}
