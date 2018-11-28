package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.ScoreRegionLog;

/**
 * @ClassName: ScoreRegionLogService
 * @description:   行政区划评分Service接口
 * @author: Jiangxiaosong
 * @date Create in 13:33 2018/2/2
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface ScoreRegionLogService {
	
	//根据评分数排列全部乡镇信息
	List<ScoreRegionLog> findScoreRegionLogSortByScore();
}
