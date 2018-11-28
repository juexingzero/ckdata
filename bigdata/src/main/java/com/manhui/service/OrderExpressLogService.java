package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.OrderExpressLog;


/**
 * @ClassName: OrderExpressLogService
 * @description: 物流日统计Service 接口
 * @author: WangSheng
 * @date Create in 10:29 2018/1/31
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface OrderExpressLogService {
	
	List<OrderExpressLog> findOrderEL(String time);
	
	//获取各大渠道包裹数
	List<OrderExpressLog> findOrderExpressNum();
	
	//保存订单物流报表数据
	void insertOrderExpress(OrderExpressLog oel);
} 