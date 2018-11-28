package com.manhui.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: DayArticleLog.java
 * @description:   商品日统计记录表
 * @author: WangSheng
 * @date Create in 14:33 2018/02/01
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class DayArticleLog {
    private Integer id;

    private Date addTime;

    private Integer articleId;

    private Integer articleNum;
    
    private BigDecimal totalAmount;
    
    private BigDecimal allTotalAmount;	//页面展示需要，总的商品销售额
    
    private Article article;	//页面展示需要，商品信息
    
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

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(Integer articleNum) {
        this.articleNum = articleNum;
    }

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getAllTotalAmount() {
		return allTotalAmount;
	}

	public void setAllTotalAmount(BigDecimal allTotalAmount) {
		this.allTotalAmount = allTotalAmount;
	}

}