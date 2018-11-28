package com.manhui.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.ReportInfo;

@Mapper
public interface ReportInfoService {
	List<Map<String,Object>> countList();
	List<ReportInfo> findReportInfoByDate(Date date);
	int saveReportInfo(ReportInfo info);
}
