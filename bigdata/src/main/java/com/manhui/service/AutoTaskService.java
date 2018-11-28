package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.AutoTask;

/**
 * @ClassName: AutoTaskService.java
 * @description:   自动任务服务类
 * @author: Jiangxiaosong
 * @date Create in 14:33 2018/3/10
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface AutoTaskService {

	//获取自动任务配置信息
	List<AutoTask> findAllAutoTask();
	
	//根据名字或者URL查询自动任务
	AutoTask findAutoTaskByTaskName(String taskName);
	
	//根据ID查询自动任务
	AutoTask findAutoTaskByTaskId(Integer id);
	
	//保存自动任务
	void saveAutoTask(AutoTask at);
	
	//保存自动任务更新
	void updateAutoTask(AutoTask at);
	
	//删除自动任务更新
	void deleteAutoTask(Integer id);
}
