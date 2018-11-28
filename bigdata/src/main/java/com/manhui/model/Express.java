package com.manhui.model;

import java.math.BigDecimal;


/**
 * @ClassName: Express
 * @description:快递物流方式信息主表
 * @author: Jiangxiaosong
 * @date Create in 15:29 2018/2/1
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class Express extends BaseObject{
    private Integer id;

    private String name;

    private String expressCode;

    private BigDecimal expressFee;

    private String website;

    private Integer sortid;

    private Integer state;

    private String remark;
    
    private String kd;		//移动端展示需要，快递名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode == null ? null : expressCode.trim();
    }

    public BigDecimal getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(BigDecimal expressFee) {
        this.expressFee = expressFee;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public Integer getSortid() {
        return sortid;
    }

    public void setSortid(Integer sortid) {
        this.sortid = sortid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getKd() {
		return kd;
	}

	public void setKd(String kd) {
		this.kd = kd;
	}

	@Override
	public String toString() {
		return "Express [id=" + id + ", name=" + name + ", expressCode=" + expressCode + ", expressFee=" + expressFee
				+ ", website=" + website + ", sortid=" + sortid + ", state=" + state + ", remark=" + remark + "]";
	}
    
}