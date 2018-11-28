package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.OrderExpressOtherLog;

@Mapper
public interface OrderExpressOtherLogService {

	OrderExpressOtherLog findnputnumAnd();
	
	//查询全部数据
	List<OrderExpressOtherLog> findAllOrderExpressOtherLog(String name);
	
	//查询收件数据
	List<OrderExpressOtherLog> findInputOrderExpressOtherLog(String name);
	
	//查询出件数据
	List<OrderExpressOtherLog> findOutputOrderExpressOtherLog(String name);
	
	//保存数据
	void saveOrderExpressOtherLog(OrderExpressOtherLog oeol);
}
