package com.manhui.model;

import java.util.Date;

/**
 * 
 * Title: OrderExpress
 * @description:订单物流信息表
 * @author Liucheng
 *
 */
public class OrderExpress {

	private Integer id;
	
	private Integer orderId;//订单id
	
	private Integer shopId;//店铺id
	
	private Integer expressId;//物流id
	
	private String expressNo;//物流单号
	
	private Date addTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getExpressId() {
		return expressId;
	}

	public void setExpressId(Integer expressId) {
		this.expressId = expressId;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Override
	public String toString() {
		return "OrderExpress [id=" + id + ", orderId=" + orderId + ", shopId=" + shopId + ", expressId=" + expressId
				+ ", expressNo=" + expressNo + ", addTime=" + addTime + "]";
	}
	
}
