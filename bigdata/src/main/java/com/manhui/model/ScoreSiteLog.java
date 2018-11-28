package com.manhui.model;

import java.util.Date;

/**
 * @ClassName: ScoreSiteLog
 * @description:   站点评分记录表
 * @author: Jiangxiaosong
 * @date Create in 13:33 2018/2/2
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class ScoreSiteLog extends BaseObject{
    private Integer id;

    private Date addTime;

    private Integer siteId;

    private Integer score;

    private String info;
    
    private String sname;

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

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getInfo() {
        return info;
    }

    public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }
}