package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.BaseRegion;

/**
 * @ClassName: BaseRegionService
 * @description:基础行政区域信息Service接口
 * @author: Jiangxiaosong
 * @date Create in 15:29 2018/2/1
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface BaseRegionService {

	//获取行政区划总数(分页用)
	int findBaseRegionCount();
	
	//获取行政区划数据(分页用)
	List<BaseRegion> findBaseRegionPage(BaseRegion region);
	
	//获取全部行政区划数据
	List<BaseRegion> findAllBaseRegion();
	
	//根据id获取行政区划数据
	BaseRegion findBaseRegionById(Integer id);
	
	//获取省会数据
	List<BaseRegion> findProvByParentCode(String parentCode);
	
	//新增行政区划数据
	void insertBaseRegion(BaseRegion region);
	
	//删除行政区划数据
	void deleteBaseRegion(Integer id);
	
	//按城市名称查询区域代码
	String getCodeByName(String name);
	
	//根据父级code和级别条件查询区域数据
	List<BaseRegion> findRegionDataByParentCodeAndLevel(String parentCode,Integer level);
}
