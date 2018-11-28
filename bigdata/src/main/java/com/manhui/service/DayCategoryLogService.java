package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.DayCategoryLog;


/**
 * @ClassName: DayCategoryLogService
 * @description: 类目日统计记录Service 接口
 * @author: WangSheng
 * @date Create in 11:29 2018/1/31
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface DayCategoryLogService {
		
	//获取所有类目销售额和销售量，商品数以及店铺数
	List<DayCategoryLog> findAllDayCategryLog(); 
	
	//查询类目价格趋势
	List<DayCategoryLog> findDayCategoryLog();
	
	//保存类目价格
	void insertDayCategoryLog(DayCategoryLog dcl);
}