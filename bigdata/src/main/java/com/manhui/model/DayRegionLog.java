package com.manhui.model;

import java.math.BigDecimal;
import java.util.Date;

public class DayRegionLog {
    private Integer id;

    private Date addTime;

    private Integer regionId;
    
    private Integer orderId;
    
    private Integer orderNum;

    private BigDecimal totalAmount;

    private BigDecimal perAmount;
    
    private BaseRegion baseRegion;	//地区信息 （页面展示需要）

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public BaseRegion getBaseRegion() {
		return baseRegion;
	}

	public void setBaseRegion(BaseRegion baseRegion) {
		this.baseRegion = baseRegion;
	}
    
    
}