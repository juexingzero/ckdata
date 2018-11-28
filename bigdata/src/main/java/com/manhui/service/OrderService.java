package com.manhui.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.model.Order;
import com.manhui.util.PageBean;

/**
 * @ClassName: OrderService
 * @description: 订单信息Service 接口
 * @author: LiuCheng
 */
@Mapper
public interface OrderService {
		//查询全部订单列表
		List<Order> getOrderList(Order order); 
		//分页订单列表
		List<Order> getPageOrder(Order order);
		//新增订单信息
		int insertOrder(Order order);
		//查看订单详细信息
		Order selectById(Integer id);
		//修改订单信息
		int updateOrder(Order order);
		//删除订单信息
		int deleteOrder(Order order);
		//查询最新5条商品订单信息
		List<Order> getTopOrder();
		//查询最新7条物流订单信息
		List<Order> getTopExpressOrder();
		//查询所有订单数
		int getCountOrder();
		//查询所有订单
		List<Order> getAllOrder();
		
		//移动端查询所有订单信息（销售信息）
		List<Order> findAllOrder(String title);
		
		//移动端根据id查询订单详细信息
		Order findOrder(Integer id);
		//根据商品id查询订单列表
		List<Order> getOrderListByArticleId(Integer articleId);
}
