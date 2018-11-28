package com.manhui.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.DayOrderLog;

/**
 * @ClassName: DayOrderLogService
 * @description: 订单信息Service 接口
 * @author: WangSheng
 * @date Create in 13:29 2018/1/30
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface DayOrderLogService {
	
	//查询某天的所有订单数据
	List<DayOrderLog> findDayOL(String time);
	
	//查询某天订单数量
	Integer findOrderNum(String time);
	
	//查询某天订单总金额
	BigDecimal findOrderMoney(String time); 
	
	//查询当月订单总金额
	BigDecimal findOrderMoneyByMonth(String month);
	
	//查询当月订单总数
	Integer findOrderNumByMonth(String month);
	
	//查询当月每天订单数
	List<DayOrderLog> findDayOrderNumByMonth(String month);
	
	//查询当月每天订单交易总额
	List<DayOrderLog> findDayOrderMoneyByMonth(String month);
	
	//查询当年订单数
	Integer findOrderNumByYear(String year);
	//查询当年交易总额
	BigDecimal findOrderMoneyByYear(String year);
}