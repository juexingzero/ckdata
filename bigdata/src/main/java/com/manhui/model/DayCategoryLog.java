package com.manhui.model;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @ClassName: DayCategoryLog.java
 * @description:   类目业日统计记录表
 * @author: WangSheng
 * @date Create in 11:33 2018/1/31
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class DayCategoryLog {
    private Integer id;

    private Date addTime;

    private Integer categoryId;

    private Integer shopNum;

    private Integer articleNum;

    private Integer orderNum;

    private Integer dealArticleNum;

    private Integer dealShopNum;

    private BigDecimal totalAmount;

    private BigDecimal perAmount;
    
    private Category category;	//类目信息（页面展示需要）
    
    private BigDecimal num;		//页面展示需要，类目平均价格

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getShopNum() {
        return shopNum;
    }

    public void setShopNum(Integer shopNum) {
        this.shopNum = shopNum;
    }

    public Integer getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(Integer articleNum) {
        this.articleNum = articleNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getDealArticleNum() {
        return dealArticleNum;
    }

    public void setDealArticleNum(Integer dealArticleNum) {
        this.dealArticleNum = dealArticleNum;
    }

    public Integer getDealShopNum() {
        return dealShopNum;
    }

    public void setDealShopNum(Integer dealShopNum) {
        this.dealShopNum = dealShopNum;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}
    
}