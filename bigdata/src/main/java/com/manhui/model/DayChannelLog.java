package com.manhui.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: DayChannelLog.java
 * @description:   渠道日统计记录表
 * @author: WangSheng
 * @date Create in 9:33 2018/1/31
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class DayChannelLog {
    private Integer id;

    private Date addTime;

    private Integer channelId;

    private Integer shopNum;

    private Integer articleNum;

    private Integer orderNum;

    private Integer dealArticleNum;

    private Integer dealShopNum;

    private BigDecimal totalAmount;

    private BigDecimal perAmount;
    
    private Channel channel;	//页面展示需要，渠道信息

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

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
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

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
    
}