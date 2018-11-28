package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.BaseOption;

/**
 * @ClassName: BaseOptionService
 * @description:公共代码Service接口
 * @author: Jiangxiaosong
 * @date Create in 15:29 2018/1/31
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface BaseOptionService {
	
	//查询所需的公共代码(枚举)
	List<BaseOption> findAllBaseOption(BaseOption baseOption);
	
	//获取所需要的公共代码的排序
	Integer findSortByBaseOption(BaseOption baseOption);
	
	//新增所需公共代码
	void insertBaseOptions(BaseOption baseOption);
	
	//删除公共代码
	void deleteBaseOptions(Integer id);
}
