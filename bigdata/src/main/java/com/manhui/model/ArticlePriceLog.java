package com.manhui.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: ArticlePriceLog.java
 * @description:   商品价格历史表
 * @author: WangSheng
 * @date Create in 16:33 2018/1/27
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class ArticlePriceLog {
    private Integer id;

    private Integer articleId;

    private BigDecimal oldPrice;

    private BigDecimal newPrice;

    private String addTime;

    private Article article;	//页面展示需要，商品信息
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

}