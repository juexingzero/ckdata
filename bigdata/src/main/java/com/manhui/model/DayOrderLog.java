package com.manhui.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: ArticlePriceLog.java
 * @description:   商品价格历史表
 * @author: WangSheng
 * @date Create in 13:33 2018/1/30
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class DayOrderLog {
    private Integer id;

    private String addTime;

    private Integer orderNum;

    private BigDecimal totalAmount;

    private BigDecimal perAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPerAmount() {
        return perAmount;
    }

    public void setPerAmount(BigDecimal perAmount) {
        this.perAmount = perAmount;
    }
}