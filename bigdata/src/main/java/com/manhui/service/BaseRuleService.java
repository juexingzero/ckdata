package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.BasePopedom;
import com.manhui.model.BaseRule;

/**
 * @ClassName: BaseRuleService
 * @description: 用户角色关联 Service 接口
 * @author: HeJiayan
 * @date Create in 15:29 2017/9/29
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface BaseRuleService {

	//新增用户角色关联关系
	void insertBaseRule(BaseRule rule);

}