package com.manhui.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.model.ScoreSiteLog;
import com.manhui.util.PageBean;

/**
 * @ClassName: ScoreSiteLogService
 * @description:   站点评分Service接口
 * @author: Jiangxiaosong
 * @date Create in 13:33 2018/2/2
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface ScoreSiteLogService {

	//根据评分数排列站点信息
	List<ScoreSiteLog> findScoreSiteLogSortByScore();
		//查询全部站点评分列表
		List<ScoreSiteLog> getScoreSiteLogList();
		//分页查询
		List<ScoreSiteLog> getPageScoreSiteLog(ScoreSiteLog ssl);
		//添加站点评分信息
		void insertScoreSiteLog(ScoreSiteLog scoreSiteLog);
		//修改站点评价
		void updateScoreSiteLog(ScoreSiteLog scoreSiteLog);
		//根据id查出改评价信息
		ScoreSiteLog selectScoreSiteLogById(Integer id);
		//删除改评价
		void deleteScoreSiteLog(Integer id);
}
