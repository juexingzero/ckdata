package com.manhui.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: Dashboard
 * @description:主页面数据展示模型
 * @author: Jiangxiaosong
 * @date Create in 15:29 2018/2/24
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class Dashboard {
	
	private String year;
	
	private String month;
	
	private BigDecimal monthSally;
	
	private Integer monthOrderNum;
	
	private Integer shopCount;
	
	private Integer articleNum;
	
	private List<String> orderNum;
	
	private List<String> orderMoney;
	
	private List<String> dayInfo;
	
	private List<Article> artList;
	
	private List<Express> expressList;
	
	private List<Channel> channelList;
	
	private List<BaseRegion> regionList;
	
	private List<AutoTask> autoTaskList;

	public Dashboard(String year, String month, BigDecimal monthSally, Integer monthOrderNum, Integer shopCount,
			Integer articleNum) {
		this.year = year;
		this.month = month;
		this.monthSally = monthSally;
		this.monthOrderNum = monthOrderNum;
		this.shopCount = shopCount;
		this.articleNum = articleNum;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public BigDecimal getMonthSally() {
		return monthSally;
	}

	public void setMonthSally(BigDecimal monthSally) {
		this.monthSally = monthSally;
	}

	public Integer getMonthOrderNum() {
		return monthOrderNum;
	}

	public void setMonthOrderNum(Integer monthOrderNum) {
		this.monthOrderNum = monthOrderNum;
	}

	public Integer getShopCount() {
		return shopCount;
	}

	public void setShopCount(Integer shopCount) {
		this.shopCount = shopCount;
	}

	public Integer getArticleNum() {
		return articleNum;
	}

	public void setArticleNum(Integer articleNum) {
		this.articleNum = articleNum;
	}

	public List<String> getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(List<String> orderNum) {
		this.orderNum = orderNum;
	}

	public List<String> getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(List<String> orderMoney) {
		this.orderMoney = orderMoney;
	}

	public List<String> getDayInfo() {
		return dayInfo;
	}

	public void setDayInfo(List<String> dayInfo) {
		this.dayInfo = dayInfo;
	}

	public List<Article> getArtList() {
		return artList;
	}

	public void setArtList(List<Article> artList) {
		this.artList = artList;
	}

	public List<Express> getExpressList() {
		return expressList;
	}

	public void setExpressList(List<Express> expressList) {
		this.expressList = expressList;
	}

	public List<Channel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}

	public List<BaseRegion> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<BaseRegion> regionList) {
		this.regionList = regionList;
	}

	public List<AutoTask> getAutoTaskList() {
		return autoTaskList;
	}

	public void setAutoTaskList(List<AutoTask> autoTaskList) {
		this.autoTaskList = autoTaskList;
	}
	
}
