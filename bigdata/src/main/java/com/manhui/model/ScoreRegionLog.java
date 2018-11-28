package com.manhui.model;

import java.util.Date;
/**
 * @ClassName: ScoreRegionLog
 * @description:   行政区划评分记录表
 * @author: Jiangxiaosong
 * @date Create in 13:33 2018/2/2
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class ScoreRegionLog extends BaseObject{
    private Integer id;

    private Date addTime;

    private Integer regionId;

    private Integer score;

    private String info;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }
}