package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.DayBusinessLog;

/**
 * @ClassName: DayBusinessLogService
 * @description: 行业日统计记录Service 接口
 * @author: WangSheng
 * @date Create in 14:29 2018/1/30
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface DayBusinessLogService {
	
	List<DayBusinessLog> findDayBL(String time);
	
	//获取订单总交易额和订单客单价格
	DayBusinessLog findAmount();
	
}