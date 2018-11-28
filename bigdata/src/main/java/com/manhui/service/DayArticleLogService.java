package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.DayArticleLog;

/**
 * @ClassName: DayArticleLogService
 * @description: 商品日统计Service 接口
 * @author: WangSheng
 * @date Create in 14:29 2018/02/01
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface DayArticleLogService {
	
	List<DayArticleLog> findDayAL(String time);
	
	//获取商品销售量
	List<DayArticleLog> findDayArticleLogData();
	
	//获取商品销售排行
	List<DayArticleLog> findDALDByMoney();
	
	//保存商品销售量
	void insertDayArticleLogData(DayArticleLog dal);
}