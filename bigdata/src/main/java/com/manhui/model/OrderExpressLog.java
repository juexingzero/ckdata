package com.manhui.model;

import java.util.Date;

/**
 * @ClassName: Article.java
 * @description:   物流信息表
 * @author: WangSheng
 * @date Create in 10:33 2018/1/31
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class OrderExpressLog {
    private Integer id;

    private Date addTime;

    private Integer expressId;

    private Integer expressNum;
    
    private Express express;	//页面展示需要，物流信息

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

    public Integer getExpressId() {
        return expressId;
    }

    public void setExpressId(Integer expressId) {
        this.expressId = expressId;
    }

    public Integer getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(Integer expressNum) {
        this.expressNum = expressNum;
    }

	public Express getExpress() {
		return express;
	}

	public void setExpress(Express express) {
		this.express = express;
	}
}