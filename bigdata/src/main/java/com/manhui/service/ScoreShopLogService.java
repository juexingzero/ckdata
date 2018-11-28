package com.manhui.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.model.ScoreShopLog;
import com.manhui.util.PageBean;

/**
 * @ClassName: ScoreShopLogService
 * @description:   店铺评分Service接口
 * @author: Jiangxiaosong
 * @date Create in 13:33 2018/2/2
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface ScoreShopLogService {

	//查询全部店铺评分列表
	List<ScoreShopLog> getScoreShopLogList();
	//分页查询
	List<ScoreShopLog> getPageScoreShopLog(ScoreShopLog ssl);
	//添加店铺评分信息
	void insertScoreShopLog(ScoreShopLog scoreShopLog);
	//修改店铺评价
	void updateScoreShopLog(ScoreShopLog scoreShopLog);
	//根据id查出改评价信息
	ScoreShopLog selectScoreShopLogById(Integer id);
	//删除改评价
	void deleteScoreShopLog(Integer id);
}
