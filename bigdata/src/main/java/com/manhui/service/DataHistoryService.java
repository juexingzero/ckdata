package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.DataHistory;

/**
 * 数据采集历史记录
 * @author Administrator
 *
 */
@Mapper
public interface DataHistoryService {
	int insertHistory(DataHistory history);
	int updateHistory(DataHistory history);
	List<DataHistory> getHistory();
}
