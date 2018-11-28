package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.DayShopLog;

/**
 * @ClassName: DayShopLogService
 * @description:   店铺日统计记录Service接口
 * @author: Jiangxiaosong
 * @date Create in 13:33 2018/2/2
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface DayShopLogService {

	//根据销售金额排名前8
	List<DayShopLog> findDayShopLogSortBytotalAmount();
	
	//保存到报表
	void insertDayShopLog(DayShopLog dsl);
}
